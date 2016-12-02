package lambdamagic.parsing.monadic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class SequentialParser<T> implements Parser<List<T>> {
	
	private List<Parser<T>> parsers;

	@SafeVarargs
	public SequentialParser(Parser<T>...parsers) {
		this.parsers = Arrays.asList(parsers);
	}

	@Override
	public Either<ParseResult<List<T>>, Exception> parse(InputStream inputStream, TextPosition position) {
		List<T> results = new ArrayList<T>();
		TextPosition newPosition = position;

		for (Parser<T> p : parsers) {
			Either<ParseResult<T>, Exception> result = p.parse(inputStream, newPosition);

			if (result.isRight())
				return Either.right(new ParseException("SequentialParser: Error occured in parsing process of " + p, result.getRight()));
			
			ParseResult<T> left = result.getLeft();
			results.add(left.getResult());
			newPosition = left.getPosition();
		}

		return Either.left(new ParseResult<List<T>>(results, newPosition));
	}

	public String toString() {
		return "SequentialParser" + Arrays.asList(parsers).toString();
	}

}