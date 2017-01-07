package jp.lambdamagic.pipeline;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.BaseStream;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;

@FunctionalInterface
public interface DataSource<T> extends Supplier<T>, AutoCloseable {

	Optional<T> readData();

	@Override
	default T get() {
		Optional<T> maybeResult = readData();
		return maybeResult.isPresent() ? maybeResult.get() : null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> DataSource<T> asDataSource(T... elements) {
		return asDataSource(Iterables.asIterable(elements));
	}

	public static <T> DataSource<T> asDataSource(Iterable<T> iterable) {
		if (iterable == null) {
			throw new NullArgumentException("iterable");
		}
		
		Iterator<T> it = iterable.iterator();

		return () -> {
			if (it.hasNext()) {
				return Optional.of(it.next());
			}
			
			return Optional.empty();
		};
	}

	public static <T> DataSource<T> asDataSource(BaseStream<T, ?> stream) {
		if (stream == null) {
			throw new NullArgumentException("stream");
		}
		
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