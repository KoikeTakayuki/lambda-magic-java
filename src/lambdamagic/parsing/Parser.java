package lambdamagic.parsing;

import lambdamagic.data.functional.Either;

@FunctionalInterface
public interface Parser<T> {

	Either<T, ? extends Exception> parse();
}