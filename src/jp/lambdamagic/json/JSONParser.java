package jp.lambdamagic.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.functional.Either;
import jp.lambdamagic.io.DataFormatException;
import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;
import jp.lambdamagic.parsing.ParserBase;
import jp.lambdamagic.text.TextPosition;

public class JSONParser extends ParserBase<JSONData> {

	public static final String JSON_NULL_STRING = "null";
	public static final char JSON_NULL_STRING_FIRST_CHARACTER = 'n';
	
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
	
	
	public static JSONParser fromString(String string) throws IOException {
		if (string == null) {
			throw new NullArgumentException("string");
		}
		
		return new JSONParser(new StringReader(string));
	}

	public JSONParser(Reader reader) throws IOException {
		super(reader);
	}
	
	public JSONParser(String string) throws IOException {
		super(string);
	}

	@Override
	public JSONData parse() throws IOException {
		return parseJSON();
	}
	
	private JSONData parseJSON() throws IOException {
		skipWhitespaces();			

		switch (getCharacter()) {
			case JSON_ARRAY_START_CHAR:
				return parseJSONArray();

			case JSON_OBJECT_START_CHAR:
				return parseJSONObject();

			case JSON_STRING_DELIMETER_CHAR:
				return parseJSONString();
					
			case JSON_NULL_STRING_FIRST_CHARACTER:
				return parseJSONNull();
					
			default:
				if (isValidNumberFirstCharacter()) {
					return parseJSONNumber();
				} else {
					return parseJSONBoolean();
				}
		}
	}
	
	private JSONObject parseJSONObject() throws IOException {	
		TextPosition positionBeforeParsing = getPosition();
		JSONObject jsonObject = new JSONObject();

		do {
			nextCharacter();
			skipWhitespaces();
				
			if (getCharacter() == JSON_OBJECT_END_CHAR) {
				return jsonObject;
			}
				
			parseKeyValue(jsonObject);

		} while (getCharacter() == JSON_OBJECT_SEPARATOR_CHAR);

		skipWhitespaces();
		
		if (getCharacter() != JSON_OBJECT_END_CHAR) {
			throw new JSONFormatException("Unclosed JSON object", positionBeforeParsing);
		}
			
		nextCharacter();

		return jsonObject;
	}
	
	private JSONArray parseJSONArray() throws IOException {
		TextPosition positionBeforeParsing = getPosition();
		JSONArray array = new JSONArray();
			
		do {
			nextCharacter();
			skipWhitespaces();
				
			if (getCharacter() == JSON_ARRAY_END_CHAR) {
				return array;
			}
				
			JSONData data = parseJSON();
			array.add(data);
			skipWhitespaces();
				
		} while (getCharacter() == JSON_ARRAY_VALUE_SEPARATOR_CHAR);

		skipWhitespaces();
			
		if (getCharacter() != JSON_ARRAY_END_CHAR) {
			throw new JSONFormatException("Unclosed JSON array", positionBeforeParsing);
		}
			
		nextCharacter();

		return array;
	}
	
	private JSONNumber parseJSONNumber() throws IOException {
		return new JSONNumber(parseNumber());
	}
	
	private JSONString parseJSONString() throws IOException {
		try {
			return new JSONString(parseString(JSON_STRING_DELIMETER_CHAR, JSON_STRING_ESCAPE_CHAR));
		} catch (DataFormatException e) {
			throw new JSONFormatException(e.getMessage());
		}
	}

	private JSONBoolean parseJSONBoolean() throws IOException {
		TextPosition positionBeforeParsing = getPosition();
		String value = parseId();

		if (value.equals(JSON_TRUE_STRING)) {
			return new JSONBoolean(true);
		}

		if (value.equals(JSON_FALSE_STRING)) {
			return new JSONBoolean(false);
		}

		throw new JSONFormatException("Unknown JSON token '" + value + "'", positionBeforeParsing);
	}
	
	private JSONNull parseJSONNull() throws IOException {
		TextPosition positionBeforeParsing = getPosition();
		String value = parseId();

		if (value.equals(JSON_NULL_STRING)) {
			return new JSONNull();
		}
		
		throw new JSONFormatException("Unknown JSON token '" + value + "'", positionBeforeParsing);
	}
	
	private Either<JSONObject, Exception> parseKeyValue(JSONObject jsonObject) throws IOException {
		String key = parseString(JSON_STRING_DELIMETER_CHAR, JSON_STRING_ESCAPE_CHAR);
		skipWhitespaces();
			
		if (getCharacter() != JSON_OBJECT_KEY_VALUE_DELIMETER_CHAR) {
			throw new JSONFormatException("JSON object key value delimeter '" + JSON_OBJECT_KEY_VALUE_DELIMETER_CHAR + "' is expected, but actual '" + (char)getCharacter() + "' is given", getPosition());
		}
		
		nextCharacter();
		skipWhitespaces();
		JSONData value = parseJSON();
		jsonObject.set(key, value);
		
		return Either.left(jsonObject);
	}
	
}