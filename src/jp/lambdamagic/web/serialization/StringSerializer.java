package jp.lambdamagic.web.serialization;

import jp.lambdamagic.data.functional.Either;
import jp.lambdamagic.io.DataFormatException;

public interface StringSerializer<T> {
	Either<T, DataFormatException> fromString(String s);
	String toString(T obj);
}
