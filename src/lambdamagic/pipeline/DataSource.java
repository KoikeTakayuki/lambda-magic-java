package lambdamagic.pipeline;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.BaseStream;

@FunctionalInterface
public interface DataSource<T> extends AutoCloseable, Supplier<T> {

	Optional<T> readData();

	@Override
	default T get() {
		Optional<T> maybeResult = readData();
		return maybeResult.isPresent() ? maybeResult.get() : null;
	}

	static <T> DataSource<T> asDataSource(Iterable<T> iterable) {
		Iterator<T> it = iterable.iterator();

		return () -> {
			if (it.hasNext()) {
				return Optional.of(it.next());
			}
			
			return Optional.empty();
		};
	}

	static <T> DataSource<T> asDataSource(BaseStream<T, ?> stream) {
		Iterator<T> it = stream.iterator();

		return () -> {
			if (it.hasNext()) {
				return Optional.of(it.next());
			}
			
			return Optional.empty();
		};
	}
	
	@Override
	default void close() throws Exception {}
	
}