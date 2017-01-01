package lambdamagic.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

import lambdamagic.NullArgumentException;
import lambdamagic.data.Tuple2;
import lambdamagic.pipeline.wrapper.FilteredDataSource;
import lambdamagic.pipeline.wrapper.InterleavedDataSource;
import lambdamagic.pipeline.wrapper.MergedDataSource;
import lambdamagic.pipeline.wrapper.TrimmedDataSource;
import lambdamagic.pipeline.wrapper.ZippedDataSource;

public class Pipeline<IN, OUT> implements DataSource<OUT> {

	private DataSource<IN> source;
	private DataProcessor<IN, OUT> processor;

	private Pipeline(DataSource<IN> source, DataProcessor<IN, OUT> processor) {
		if (source == null) {
			throw new NullArgumentException("source");
		}

		this.source = source;
		this.processor = processor;
	}

	public static <T> Pipeline<T, T> from(DataSource<T> newSource) {
		return new Pipeline<T, T>(newSource, null);
	}

	public static <T> Pipeline<T, T> from(BaseStream<T, ?> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <T> Pipeline<T, T> from(Iterable<T> newSource) {
		return from(DataSource.asDataSource(newSource));
	}

	public static <T1, T2, T3> DataProcessor<T1, T3> compose(DataProcessor<T1, T2> p1, DataProcessor<T2, T3> p2) {
		return data -> p2.process(p1.process(data));
	}

	@SuppressWarnings("unchecked")
	public <NEW_OUT> Pipeline<IN, NEW_OUT> to(DataProcessor<OUT, NEW_OUT> newProcessor) {
		if (newProcessor == null) {
			throw new NullArgumentException("newProcessor");
		}

		if (processor == null) {
			return new Pipeline<IN, NEW_OUT>(source, (DataProcessor<IN, NEW_OUT>)newProcessor);
		}

		return new Pipeline<IN, NEW_OUT>(source, compose(processor, newProcessor));
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
	@Override
	public Optional<OUT> readData() {
		Optional<IN> maybeData = source.readData();

		if (maybeData.isPresent()) {
			IN data = maybeData.get();

			if (processor == null) {
				return Optional.of((OUT)data);
			}

			return Optional.of(processor.process(data));
		}

		return Optional.empty();
	}

	public <OTHER_OUT> Pipeline<IN, OUT> fork(DataProcessor<OUT, OTHER_OUT> otherProcessor) {
		if (otherProcessor == null) {
			throw new NullArgumentException("otherProcessor");
		}
		
		DataProcessor<IN, OUT> newProcessor = compose(processor, (output) -> {
			otherProcessor.process(output);
			return output;
		});

		return new Pipeline<IN, OUT>(source, newProcessor);
	}

	public Pipeline<IN, OUT> fork(Consumer<OUT> consumer) {
		return fork(toProcessor(consumer));
	}

	public void execute() {
		Optional<OUT> maybeData = readData();

		while (maybeData.isPresent()) {
			maybeData = readData();
		}
	}

	public Pipeline<OUT, OUT> trim(int trimCount) {
		return from(new TrimmedDataSource<OUT>(this, trimCount));
	}

	public Pipeline<OUT, OUT> filter(Predicate<OUT> predicate) {
		return from(new FilteredDataSource<OUT>(this, predicate));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<OUT, OUT> interleave(DataSource<OUT>... sources) {
		return from(new InterleavedDataSource<OUT>(sources));
	}

	@SuppressWarnings("unchecked")
	public final Pipeline<OUT, OUT> merge(final DataSource<OUT>... sources) {
		return from(new MergedDataSource<OUT>(sources));
	}

	public <T> Pipeline<Tuple2<OUT, T>, Tuple2<OUT, T>> zip(DataSource<T> other) {
		return from(new ZippedDataSource<OUT, T>(this, other));
	}

	public <T> T fold(T seed, BiFunction<T, OUT, T> function) {
		T result = seed;
		Optional<OUT> maybeData = readData();

		while (maybeData.isPresent()) {
			result = function.apply(result, maybeData.get());
			maybeData = readData();
		}

		return result;
	}
	
	public List<OUT> toList() {
		List<OUT> result = new ArrayList<OUT>();
		Optional<OUT> maybeData = readData();

		while (maybeData.isPresent()) {
			result.add(maybeData.get());
			maybeData = readData();
		}
		
		return result;
	}

	public Pipeline<IN, OUT> print() {
		return to(data -> {
			System.out.println(data);
			return data;
		});
	}
	
	@Override
	public void close() throws Exception {
		source.close();
		processor.close();
	}
}