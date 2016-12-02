package lambdamagic.pipeline;

import java.io.Closeable;
import java.io.IOException;

@FunctionalInterface
public interface DataProcessor<T1, T2> extends Closeable {
	T2 process(T1 data);

	@Override
	default void close() throws IOException {}
}