package lambdamagic.json;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class JSONArrayParser implements Parser<Object> {

	@SuppressWarnings("unchecked")
	@Override
	public Either<ParseResult<Object>, Exception> parse(Reader reader, TextPosition position) {
		return null;
	}

}
