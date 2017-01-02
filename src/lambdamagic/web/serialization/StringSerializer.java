package lambdamagic.web.serialization;

import lambdamagic.data.functional.Either;
import lambdamagic.io.DataFormatException;

public interface StringSerializer<T> {
	Either<T, DataFormatException> fromString(String s);
	String toString(T obj);
}
