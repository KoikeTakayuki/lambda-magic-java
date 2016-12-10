package lambdamagic.parsing.combinator;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class RepetitiveParser<T> implements Parser<T> {

	@Override
	public Either<ParseResult<T>, Exception> parse(Reader reader, TextPosition position) {
		// TODO Auto-generated method stub
		return null;
	}

}
