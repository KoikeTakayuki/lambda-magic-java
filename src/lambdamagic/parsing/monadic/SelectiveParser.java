package lambdamagic.parsing.monadic;

import java.io.InputStream;
import java.util.Arrays;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class SelectiveParser<T> implements Parser<T> {

	private Iterable<Parser<T>> parsers;

	@SafeVarargs
	public SelectiveParser(Parser<T>... parsers) {
		this.parsers = Arrays.asList(parsers);
	}

	@Override
	public Either<ParseResult<T>, Exception> parse(InputStream inputStream, TextPosition position) {
		for (Parser<T> p : parsers) {
			Either<ParseResult<T>, Exception> result = p.parse(inputStream, position);

			if (result.isLeft())
				return result;

			if (result.isRight()) {
				Exception e = result.getRight();

				if (!(e instanceof ParseException)) {
					return Either.right(new Exception("SelectiveParser: exception which isn't ParseException occured", e));
				}
			}
		}

		return Either.right(new ParseException("SelectiveParser: all parsers failed to parse", position));
	}
}
