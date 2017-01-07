package jp.lambdamagic.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.Tuple2;
import jp.lambdamagic.pipeline.wrapper.FilteredDataSource;
import jp.lambdamagic.pipeline.wrapper.InterleavedDataSource;
import jp.lambdamagic.pipeline.wrapper.MergedDataSource;
import jp.lambdamagic.pipeline.wrapper.TrimmedDataSource;
import jp.lambdamagic.pipeline.wrapper.ZippedDataSource;

public class Pipeline<O> implements DataSource<O>, Runnable {

	private DataSource<O> source;

	private Pipeline(DataSource<O> source) {
		if (source == null) {
			throw new NullArgumentException("source");
		}

		this.source = source;
	}

	public static <T> Pipeline<T> from(DataSource<T> newSource) {
		return new Pipeline<T>(newSource);
	}

	public static <T> Pipeline<T> from(BaseStream<T, ?> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <T> Pipeline<T> from(Iterable<T> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <T1, T2, T3> DataProcessor<T1, T3> compose(DataProcessor<T1, T2> p1, DataProcessor<T2, T3> p2) {
		return data -> p2.process(p1.process(data));
	}

	public <T> Pipeline<T> map(DataProcessor<O, T> processor) {
		if (processor == null) {
			throw new NullArgumentException("processor");
		}

		return from(() -> {
			return source.readData().map(processor);
		});
	}

	@Override
	public Optional<O> readData() {
		return source.readData();
	}

	public <T> Pipeline<O> forEach(DataProcessor<O, T> processor) {
		if (processor == null) {
			throw new NullArgumentException("processor");
		}
		
		return from(() -> {
			Optional<O> data = source.readData();
			data.map(processor);
			
			return data;
		});
	}

	public Pipeline<O> forEach(Consumer<O> consumer) {
		return forEach(toProcessor(consumer));
	}
	
	@Override
	public void run() {
		Optional<O> data = readData();

		while (data.isPresent()) {
			data = readData();
		}
	}

	public Pipeline<O> trim(int trimCount) {
		return from(new TrimmedDataSource<O>(this, trimCount));
	}

	public Pipeline<O> filter(Predicate<O> predicate) {
		return from(new FilteredDataSource<O>(this, predicate));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O> interleave(DataSource<O>... sources) {
		return from(new InterleavedDataSource<O>(construct(this, sources)));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O> merge(DataSource<O>... sources) {
		return from(new MergedDataSource<O>(construct(this, sources)));
	}

	public <T> Pipeline<Tuple2<O, T>> zip(DataSource<T> other) {
		return from(new ZippedDataSource<O, T>(this, other));
	}

	public <T> T fold(T seed, BiFunction<T, O, T> function) throws Exception {
		T result = seed;
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			result = function.apply(result, maybeData.get());
			maybeData = readData();
		}
		
		close();

		return result;
	}
	
	public List<O> toList() throws Exception {
		List<O> result = new ArrayList<O>();
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			result.add(maybeData.get());
			maybeData = readData();
		}
		
		close();
		
		return result;
	}

	public Pipeline<O> print() {
		return forEach(e -> {
			System.out.println(e);
		});
	}
	
	@Override
	public void close() throws Exception {
		source.close();
	}
	
	private <T> DataProcessor<T, T> toProcessor(Consumer<T> consumer) {
		if (consumer == null) {
			throw new NullArgumentException("consumer");
		}

		return output -> {
			consumer.accept(output);
			return output;
		};
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataSource<T>[] construct(DataSource<T> first, DataSource<T>[] rest) {
		DataSource<T>[] newSources = new DataSource[rest.length + 1];
		
		newSources[0] = first;
		
		for (int i = 0; i < rest.length; ++i) {
			newSources[i + 1] = rest[i];
		}
		
		return newSources;	
	}
	
}