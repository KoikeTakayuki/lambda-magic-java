package lambdamagic.parsing;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.combinator.ParserMapping;
import lambdamagic.parsing.combinator.SelectiveParser;
import lambdamagic.text.TextPosition;

public class BooleanParser implements Parser<Boolean> {

	private Parser<Boolean> parser;
	
	@SuppressWarnings("unchecked")
	public BooleanParser(String trueString, String falseString) {
		this.parser = new ParserMapping<String, Boolean>(
						new SelectiveParser<String>(
							new TokenParser(trueString),
							new TokenParser(falseString)),
						result -> {
							return result.equals(trueString) ? true : false;
						});
	}

	@Override
	public Either<ParseResult<Boolean>, Exception> parse(Reader reader, TextPosition position) {
		return parser.parse(reader, position);
	}
}
