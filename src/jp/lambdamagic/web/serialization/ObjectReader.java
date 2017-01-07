package jp.lambdamagic.web.serialization;

import java.io.Closeable;

import jp.lambdamagic.data.functional.Either;

public interface ObjectReader extends Closeable {
	Either<Object, ? extends Exception> readObject();
}
