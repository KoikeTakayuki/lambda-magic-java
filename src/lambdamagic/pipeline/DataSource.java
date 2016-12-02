package lambdamagic.pipeline;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.BaseStream;

@FunctionalInterface
public interface DataSource<T> extends Closeable {

	Optional<T> readData();

	static <T> DataSource<T> asDataSource(Iterable<T> iterable) {
		Iterator<T> it = iterable.iterator();

		return () -> {
			if (it.hasNext())
				return Optional.of(it.next());
			
			return Optional.empty();
		};
	}

	static <T> DataSource<T> asDataSource(BaseStream<T, ?> stream) {
		Iterator<T> it = stream.iterator();

		return () -> {
			if (it.hasNext())
				return Optional.of(it.next());
			
			return Optional.empty();
		};
	}
	
	@Override
	default void close() throws IOException {}
}