package jp.lambdamagic.pipeline;

import java.util.function.Function;

@FunctionalInterface
public interface DataProcessor<I, O> extends Function<I, O>, AutoCloseable {

	O process(I input);

	@Override
	default O apply(I input) {
		return process(input);
	}
	
	@Override
	default void close() throws Exception {}
	
}