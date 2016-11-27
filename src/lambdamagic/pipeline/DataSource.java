package lambdamagic.pipeline;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

@FunctionalInterface
public interface DataSource<T> {

	Optional<T> readData();

	static <T> DataSource<T> asDataSource(Stream<T> stream) {
		return () -> {
			return stream.findFirst();
		};
	}

	static <T> DataSource<T> asDataSource(Iterable<T> iterable) {
		Iterator<T> it = iterable.iterator();

		return () -> {
			if (it.hasNext())
				return Optional.of(it.next());
			
			return Optional.empty();
		};
	}
}