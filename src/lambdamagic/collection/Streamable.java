package lambdamagic.collection;

import java.util.stream.Stream;

public interface Streamable<T> {

	Stream<T> stream();
}
