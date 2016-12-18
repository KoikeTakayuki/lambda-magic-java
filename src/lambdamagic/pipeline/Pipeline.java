package lambdamagic.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

import lambdamagic.NullArgumentException;
import lambdamagic.data.Tuple2;
import lambdamagic.pipeline.composite.FilteredDataSource;
import lambdamagic.pipeline.composite.InterleavedDataSource;
import lambdamagic.pipeline.composite.MergedDataSource;
import lambdamagic.pipeline.composite.TrimmedDataSource;
import lambdamagic.pipeline.composite.ZippedDataSource;

public class Pipeline<I, O> implements DataSource<O> {

	private DataSource<I> source;
	private DataProcessor<I, O> processor;

	private Pipeline(DataSource<I> source, DataProcessor<I, O> processor) {
		if (source == null)
			throw new NullArgumentException("source");

		this.source = source;
		this.processor = processor;
	}

	public static <I> Pipeline<I, I> from(DataSource<I> newSource) {
		return new Pipeline<I, I>(newSource, null);
	}

	public static <I> Pipeline<I, I> from(BaseStream<I, ?> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <I> Pipeline<I, I> from(Iterable<I> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <T1, T2, T3> DataProcessor<T1, T3> compose(DataProcessor<T1, T2> p1, DataProcessor<T2, T3> p2) {
		return data -> p2.process(p1.process(data));
	}

	@SuppressWarnings("unchecked")
	public <O2> Pipeline<I, O2> to(DataProcessor<O, O2> newProcessor) {
		if (newProcessor == null)
			throw new NullArgumentException("newProcessor");

		if (processor == null)
			return new Pipeline<I, O2>(source, (DataProcessor<I, O2>)newProcessor);

		return new Pipeline<I, O2>(source, compose(processor, newProcessor));
	}

	public Pipeline<I, O> to(Consumer<O> consumer) {
		return to(toProcessor(consumer));
	}

	private <T> DataProcessor<T, T> toProcessor(Consumer<T> consumer) {
		if (consumer == null)
			throw new NullArgumentException("consumer");

		return output -> {
			consumer.accept(output);
			return output;
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<O> readData() {
		Optional<I> maybeData = source.readData();

		if (maybeData.isPresent()) {
			I data = maybeData.get();

			if (processor == null)
				return Optional.of((O)data);

			return Optional.of(processor.process(data));
		}

		return Optional.empty();
	}

	public <O2> Pipeline<I, O> fork(DataProcessor<O, O2> otherProcessor) {
		if (otherProcessor == null)
			throw new NullArgumentException("otherProcessor");
		
		DataProcessor<I, O> newProcessor = compose(processor, (output) -> {
			otherProcessor.process(output);
			return output;
		});

		return new Pipeline<I, O>(source, newProcessor);
	}

	public Pipeline<I, O> fork(Consumer<O> consumer) {
		return fork(toProcessor(consumer));
	}

	public void execute() {
		Optional<O> maybeData = readData();

		while (maybeData.isPresent())
			maybeData = readData();
	}

	public Pipeline<O, O> trim(int count) {
		return from(new TrimmedDataSource<O>(this, count));
	}

	public Pipeline<O, O> filter(Predicate<O> predicate) {
		return from(new FilteredDataSource<O>(this, predicate));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O, O> interleave(DataSource<O>... sources) {
		DataSource<O>[] args = new DataSource[sources.length + 1];
		args[0] = this;
		for (int i = 0; i < sources.length; ++i) {
			args[i + 1] = sources[i];
		}

		return from(new InterleavedDataSource<O>(args));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<O, O> merge(final DataSource<O>... sources) {
		DataSource<O>[] args = new DataSource[sources.length + 1];
		args[0] = this;
		for (int i = 0; i < sources.length; ++i) {
			args[i + 1] = sources[i];
		}

		return from(new MergedDataSource<O>(sources));
	}

	public <O2> Pipeline<Tuple2<O, O2>, Tuple2<O, O2>> zip(DataSource<O2> other) {
		return from(new ZippedDataSource<O, O2>(this, other));
	}

	public <T> T fold(T seed, BiFunction<T, O, T> function) {
		T result = seed;
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			result = function.apply(result, maybeData.get());
			maybeData = readData();
		}

		return result;
	}
	
	public List<O> toList() {
		List<O> result = new ArrayList<O>();
		Optional<O> maybeData = readData();

		while (maybeData.isPresent()) {
			result.add(maybeData.get());
			maybeData = readData();
		}
		
		return result;
	}

	public Pipeline<I, O> print() {
		return to(toProcessor(data -> System.out.println(data)));
	}

	@SuppressWarnings("unchecked")
	public <T> Pipeline<I, T> cast() {
		return to(data -> (T)data);
	}
	
	@Override
	public void close() throws IOException {
		source.close();
		processor.close();
	}
}