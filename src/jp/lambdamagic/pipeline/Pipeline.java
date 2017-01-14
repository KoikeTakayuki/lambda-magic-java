package jp.lambdamagic.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;
import jp.lambdamagic.data.Tuple2;

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

	public static <T> Pipeline<T> from(BaseStream<T, ?> stream) {
		return from(DataSource.asDataSource(stream));
	}

	public static <T> Pipeline<T> from(Iterable<T> iterable) {
		return from(DataSource.asDataSource(iterable));
	}

	@Override
	public Optional<O> readData() {
		return source.readData();
	}
	
	@Override
	public void close() throws Exception {
		source.close();
	}
	
	@Override
	public void run() {
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			maybeData = readData();
		}
		
		try {
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> Pipeline<T> map(DataProcessor<O, T> processor) {
		if (processor == null) {
			throw new NullArgumentException("processor");
		}

		return from(() -> source.readData().map(processor));
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
		if (consumer == null) {
			throw new NullArgumentException("consumer");
		}
		
		return forEach(input -> {
			consumer.accept(input);
			return input;
		});
	}

	public Pipeline<O> filter(Predicate<O> predicate) {
		return from(new FilteredDataSource<O>(this, predicate));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O> interleave(DataSource<O>... sources) {
		return from(new InterleavedDataSource<>(Iterables.construct(this, sources)));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O> merge(DataSource<O>... sources) {
		return from(new MergedDataSource<>(Iterables.construct(this, sources)));
	}

	public <T> Pipeline<Tuple2<O, T>> zip(DataSource<T> other) {
		return from(new ZippedDataSource<>(this, other));
	}
	
	public Pipeline<O> repeat() {
		return from(new RepetitiveDataSource<>(this));
	}
	
	public Pipeline<O> trim(int trimCount) {
		return from(new TrimmedDataSource<>(this, trimCount));
	}
	
	public Pipeline<O> print() {
		return forEach(e -> {
			System.out.println(e);
		});
	}

	public <T> T fold(T seed, BiFunction<T, O, T> foldingFunction) throws Exception {
		if (foldingFunction == null) {
			throw new NullArgumentException("foldingFunction");
		}
		
		T result = seed;
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			result = foldingFunction.apply(result, maybeData.get());
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

}