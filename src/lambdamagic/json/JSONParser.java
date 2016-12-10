package lambdamagic.json;

import java.io.Reader;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.BooleanParser;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.parsing.combinator.SelectiveParser;
import lambdamagic.text.TextPosition;

public class JSONParser implements Parser<Object> {
	
	public static String JSON_NULL_STRING = "null";
	
	public static String JSON_TRUE_STRING = "true";
	public static String JSON_FALSE_STRING = "false";
	
	public static char JSON_STRING_DELIMETER_CHAR = '"'; 
	
	public static char JSON_ARRAY_START_CHAR = '[';
	public static char JSON_ARRAY_END_CHAR = ']';
	public static char JSON_ARRAY_VALUE_SEPARATOR_CHAR = ',';
	
	public static char JSON_OBJECT_START_CHAR = '{';
	public static char JSON_OBJECT_END_CHAR = '}';
	public static char JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR = ':';
	
	public JSONParser() {
	}

	@Override
	public Either<ParseResult<Object>, Exception> parse(Reader reader, TextPosition position) {
		// TODO Auto-generated method stub
		return null;
	}

}
