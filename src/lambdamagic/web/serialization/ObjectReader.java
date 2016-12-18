package lambdamagic.web.serialization;

import java.io.Closeable;
import java.io.IOException;

import lambdamagic.data.functional.Either;
import lambdamagic.io.DataFormatException;

public interface ObjectReader extends Closeable {

	Either<Object, ? extends Exception> readObject();
}
