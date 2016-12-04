package lambdamagic.pipeline;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface DataProcessor<I, O> extends Closeable, Function<I, O> {

	O process(I input);

	@Override
	default void close() throws IOException {}

	@Override
	default O apply(I input) {
		return process(input);
	}
}