package lambdamagic.json;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.BooleanParser;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.parsing.TokenParser;
import lambdamagic.parsing.combinator.ParserMapping;
import lambdamagic.parsing.combinator.SelectiveParser;
import lambdamagic.text.TextPosition;

public class JSONBooleanParser implements Parser<Object> {
	
	private static String JSON_TRUE = "true";
	private static String JSON_FALSE = "false";
	
	private Parser<Boolean> parser;
	
	
	public JSONBooleanParser() {
		parser = new BooleanParser(JSON_TRUE, JSON_FALSE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Either<ParseResult<Object>, Exception> parse(Reader reader, TextPosition position) {
		return null;
	}

}
