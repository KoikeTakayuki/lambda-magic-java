package lambdamagic.web.serialization;

import java.io.Closeable;

import lambdamagic.data.functional.Either;

public interface ObjectReader extends Closeable {
	Either<Object, ? extends Exception> readObject();
}
