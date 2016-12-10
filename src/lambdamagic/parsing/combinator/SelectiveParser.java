package lambdamagic.parsing.combinator;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class SelectiveParser<T> implements Parser<T> {

	private Parser<T>[] parsers;

	@SuppressWarnings("unchecked")
	public SelectiveParser(Parser<T>... parsers) {
		this.parsers = parsers;
	}

	@Override
	public Either<ParseResult<T>, Exception> parse(Reader reader, TextPosition position) {
		for (Parser<T> p : parsers) {
			Either<ParseResult<T>, Exception> result = p.parse(reader, position);

			if (result.isLeft())
				return result;

			if (result.isRight()) {
				Exception e = result.getRight();

				if (!(e instanceof ParseException)) {
					return Either.right(new Exception("SelectiveParser: exception which isn't ParseException has occured", e));
				}
			}
		}

		return Either.right(new ParseException("SelectiveParser: all parsers failed to parse", position));
	}
}
