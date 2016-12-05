package lambdamagic.parsing;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.text.TextPosition;

@FunctionalInterface
public interface Parser<T> {
	Either<ParseResult<T>, Exception> parse(Reader reader, TextPosition position);
}