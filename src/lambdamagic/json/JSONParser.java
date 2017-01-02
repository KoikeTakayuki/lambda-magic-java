package lambdamagic.json;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParserBase;
import lambdamagic.web.serialization.ObjectReader;

public class JSONParser extends ParserBase<Object> implements ObjectReader {

	public static final String JSON_NULL_STRING = "null";
	
	public static final String JSON_TRUE_STRING = "true";
	public static final String JSON_FALSE_STRING = "false";
	
	public static final char JSON_STRING_DELIMETER_CHAR = '"'; 
	public static final char JSON_STRING_ESCAPE_CHAR = '\\';
	
	public static final char JSON_ARRAY_START_CHAR = '[';
	public static final char JSON_ARRAY_END_CHAR = ']';
	public static final char JSON_ARRAY_VALUE_SEPARATOR_CHAR = ',';
	
	public static final char JSON_OBJECT_START_CHAR = '{';
	public static final char JSON_OBJECT_END_CHAR = '}';
	public static final char JSON_OBJECT_KEY_VALUE_DELIMETER_CHAR = ':';
	public static final char JSON_OBJECT_SEPARATOR_CHAR = ',';


	public JSONParser(Reader reader) throws IOException {
		super(reader);
	}
	
	public JSONParser(String string) throws IOException {
		super(string);
	}

	@Override
	public Either<Object, Exception> parse() {
		return parseJSON();
	}
	
	private Either<Object, Exception> parseJSON() {
		
		try {
			skipWhitespaces();			
			Either<?, Exception> resultOrException;

			switch (getCharacter()) {
				case JSON_ARRAY_START_CHAR:
					resultOrException = parseArray();
					break;

				case JSON_OBJECT_START_CHAR:
					resultOrException = parseObject();
					break;

				case JSON_STRING_DELIMETER_CHAR:
					resultOrException = parseString(JSON_STRING_DELIMETER_CHAR, JSON_STRING_ESCAPE_CHAR);
					break;
					
				default:
					if (isValidNumberFirstCharacter()) {
						resultOrException = parseNumber();
					} else {
						resultOrException = parseBooleanOrNull();
					}
			}
			
			return resultOrException.applyToLeft(o -> {
				
				return (o == null) ? new JSONNull() : (Object)o;
			});
			
		} catch (IOException e) {
			return Either.right(e);
		}
	}

	public Either<Boolean, Exception> parseBooleanOrNull() {

		Either<String, Exception> valueOrException = parseId();

		if (valueOrException.isRight()) {
			return Either.right(valueOrException.getRight());
		}
		
		String value = valueOrException.getLeft();

		if (value.equals(JSON_TRUE_STRING)) {
			return Either.left(true);
		}

		if (value.equals(JSON_FALSE_STRING)) {
			return Either.left(false);
		}

		if (value.equals(JSON_NULL_STRING)) {
			return Either.left(null);
		}
		
		return Either.right(new JSONFormatException(getPosition()));
	}
	
	private Either<Map<String, Object>, Exception> parseKeyValue(Map<String, Object> jsonObject) {
		try {

			Either<String, Exception> keyOrException = parseString(JSON_STRING_DELIMETER_CHAR, JSON_STRING_ESCAPE_CHAR);
	
			if (keyOrException.isRight())
				return Either.right(keyOrException.getRight());
	
			String key = keyOrException.getLeft();
			
			skipWhitespaces();
			
			if (getCharacter() != JSON_OBJECT_KEY_VALUE_DELIMETER_CHAR) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			nextCharacter();
			skipWhitespaces();

			Either<Object, Exception> valueOrException = parseJSON();

			if (valueOrException.isRight()) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			jsonObject.put(key, valueOrException.getLeft());
			
			return Either.left(jsonObject);

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	private Either<Map<String, Object>, Exception> parseObject() {
		try {

			if (getCharacter() != JSON_OBJECT_START_CHAR) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			Map<String, Object> jsonObject = new LinkedHashMap<String, Object>();
			Either<Map<String, Object>, Exception> jsonObjectOrException;

			do {
				nextCharacter();
				skipWhitespaces();
				
				jsonObjectOrException = parseKeyValue(jsonObject);

				if (jsonObjectOrException.isRight()) {
					return jsonObjectOrException;
				}

			} while (getCharacter() == JSON_OBJECT_SEPARATOR_CHAR);

			if (getCharacter() != JSON_OBJECT_END_CHAR) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			nextCharacter();

			return jsonObjectOrException;

		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	private Either<List<Object>, Exception> parseArray() {
		try {
		
			if (getCharacter() != JSON_ARRAY_START_CHAR) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			List<Object> array = new ArrayList<Object>();
			
			do {

				nextCharacter();
				skipWhitespaces();
				
				Either<Object, Exception> objectOrException = parseJSON();
	
				if (objectOrException.isRight()) {
					return Either.right(objectOrException.getRight());
				}
				
				array.add(objectOrException.getLeft());
				
			} while (getCharacter() == JSON_ARRAY_VALUE_SEPARATOR_CHAR);

			if (getCharacter() != JSON_ARRAY_END_CHAR) {
				return Either.right(new JSONFormatException(getPosition()));
			}
			
			nextCharacter();

			return Either.left(array);

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	@Override
	public Either<Object, Exception> readObject() {
		return parse();
	}
	
}