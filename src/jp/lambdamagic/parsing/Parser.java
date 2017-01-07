package jp.lambdamagic.parsing;

import jp.lambdamagic.data.functional.Either;

@FunctionalInterface
public interface Parser<T> {
	Either<T, ? extends Exception> parse();
}