package lambdamagic.parsing;

import java.io.InputStream;

import lambdamagic.data.functional.Either;
import lambdamagic.text.TextPosition;

@FunctionalInterface
public interface Parser<T> {
	Either<ParseResult<T>, Exception> parse(InputStream inputStream, TextPosition position);
}