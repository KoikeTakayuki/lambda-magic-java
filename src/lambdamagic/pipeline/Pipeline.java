package lambdamagic.pipeline;

import java.util.Optional;
import java.util.function.Consumer;

import lambdamagic.NullArgumentException;

public class Pipeline<I, O> implements DataSource<O> {

	private DataSource<I> source;
	private DataProcessor<I, O> processor;

	public DataSource<I> getDataSource() {
		return source;
	}

	public DataProcessor<I, O> getProcessor() {
		return processor;
	}

	private Pipeline(DataSource<I> source, DataProcessor<I, O> processor) {
		if (source == null)
			throw new NoDataSourceException();

		this.source = source;
		this.processor = processor;
	}

	public static <I> Pipeline<I, I> from(DataSource<I> newSource) {
		return new Pipeline<I, I>(newSource, null);
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

	public static <T1, T2, T3> DataProcessor<T1, T3> compose
		(DataProcessor<T1, T2> p1, DataProcessor<T2, T3> p2) {

		return data -> p2.process(p1.process(data));
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
}