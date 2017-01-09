package jp.lambdamagic.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;

public class JSONParserTest {

	@Test(expected=NullArgumentException.class)
	public void fromString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() throws IOException {
		JSONParser.fromString(null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONParser_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
		new JSONParser((String)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONParser_mustThrowNullArgumentExceptionWhenNullReaderIsGiven() throws IOException {
		new JSONParser((Reader)null);
	}
	
	@Test
	public void parse_parseNumber() throws IOException {
		JSONData parseResult = JSONParser.fromString("1").parse();
		assertThat(parseResult, is(instanceOf(JSONNumber.class)));
		JSONNumber number = (JSONNumber)parseResult;
		assertThat(number.getValue(), is(1.0));
	}
	
	@Test
	public void parse_parseNegativeJSONNumber() throws IOException {
		JSONData parseResult = JSONParser.fromString("-1").parse();
		assertThat(parseResult, is(instanceOf(JSONNumber.class)));
		JSONNumber number = (JSONNumber)parseResult;
		assertThat(number.getValue(), is(-1.0));
	}
	
	@Test
	public void parse_parseDecimalJSONNumber() throws IOException {
		JSONData parseResult = JSONParser.fromString("1.11").parse();
		assertThat(parseResult, is(instanceOf(JSONNumber.class)));
		JSONNumber number = (JSONNumber)parseResult;
		assertThat(number.getValue(), is(1.11));
	}
	
	@Test
	public void parse_parseNormalizedJSONNumber() throws IOException {
		JSONData parseResult = JSONParser.fromString("1e+10").parse();
		assertThat(parseResult, is(instanceOf(JSONNumber.class)));
		JSONNumber number = (JSONNumber)parseResult;
		assertThat(number.getValue(), is(1e+10));
	}
	
	@Test
	public void parse_parseNull() throws IOException {
		JSONData parseResult = JSONParser.fromString("null").parse();
		assertThat(parseResult, is(instanceOf(JSONNull.class)));
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenInvalidTokenAfterNullIsGiven() {
		try {
			JSONParser.fromString("nulltest").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unknown JSON token 'nulltest' at (1, 1)"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void parse_parseTrue() throws IOException {
		JSONData parseResult = JSONParser.fromString("true").parse();
		assertThat(parseResult, is(instanceOf(JSONBoolean.class)));
		JSONBoolean bool = (JSONBoolean)parseResult;
		assertThat(bool.getValue(), is(true));
	}
	
	@Test
	public void parse_parseFalse() throws IOException {
		JSONData parseResult = JSONParser.fromString("false").parse();
		assertThat(parseResult, is(instanceOf(JSONBoolean.class)));
		JSONBoolean bool = (JSONBoolean)parseResult;
		assertThat(bool.getValue(), is(false));
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenInvalidBooleanTokenIsGiven() {
		try {
			JSONParser.fromString("falsetest").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unknown JSON token 'falsetest' at (1, 1)"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenInvalidTokenIsGiven() {
		try {
			JSONParser.fromString("test").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unknown JSON token 'test' at (1, 1)"));
		} catch (Exception e) {
			fail();
		} 
	}
	
	@Test
	public void parse_parseString() throws IOException {
		JSONData parseResult = JSONParser.fromString("\"JSONString\"").parse();
		assertThat(parseResult, is(instanceOf(JSONString.class)));
		JSONString string = (JSONString)parseResult;
		assertThat(string.getValue(), is("JSONString"));
	}
	
	@Test
	public void parse_parseStringWhichIncludsSpecialCharacter() throws IOException {
		JSONData parseResult = JSONParser.fromString("\"\\nJSONString\\n\"").parse();
		assertThat(parseResult, is(instanceOf(JSONString.class)));
		JSONString string = (JSONString)parseResult;
		assertThat(string.getValue(), is("\nJSONString\n"));
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenUnclosedJSONStringIsGiven() {
		try {
			JSONParser.fromString("\"test").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unclosed string at (1, 1)"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void parse_parseArray() throws IOException {
		JSONData parseResult = JSONParser.fromString("[1, 2, 3]").parse();
		assertThat(parseResult, is(instanceOf(JSONArray.class)));
		JSONArray array = (JSONArray)parseResult;
		assertThat(array.size(), is(3));
		assertThat(array.get(0), is(instanceOf(JSONNumber.class)));
		assertThat(((JSONNumber)array.get(0)).getValue(), is(1.0));
		assertThat(array.get(1), is(instanceOf(JSONNumber.class)));
		assertThat(((JSONNumber)array.get(1)).getValue(), is(2.0));
		assertThat(array.get(2), is(instanceOf(JSONNumber.class)));
		assertThat(((JSONNumber)array.get(2)).getValue(), is(3.0));
	}
	
	@Test
	public void parse_parseEmptyArray() throws IOException {
		JSONData parseResult = JSONParser.fromString("[]").parse();
		assertThat(parseResult, is(instanceOf(JSONArray.class)));
		JSONArray array = (JSONArray)parseResult;
		assertThat(array.size(), is(0));
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenUnclosedJSONArrayIsGiven() {
		try {
			JSONParser.fromString(" [\"test\"").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unclosed JSON array at (1, 2)"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void parse_parseObject() throws IOException {
		JSONData parseResult = JSONParser.fromString("{\"a\": \"test\", \"b\": 100}").parse();
		assertThat(parseResult, is(instanceOf(JSONObject.class)));
		
		JSONObject object = (JSONObject)parseResult;
		assertThat(object.get("a").isPresent(), is(true));
		assertThat(object.get("a").get(), is(instanceOf(JSONString.class)));
		assertThat(((JSONString)object.get("a").get()).getValue(), is("test"));
		
		assertThat(object.get("b").isPresent(), is(true));
		assertThat(object.get("b").get(), is(instanceOf(JSONNumber.class)));
		assertThat(((JSONNumber)object.get("b").get()).getValue(), is(100.0));
	}
	
	@Test
	public void parse_parseEmptyObject() throws IOException {
		JSONData parseResult = JSONParser.fromString("{}").parse();
		assertThat(parseResult, is(instanceOf(JSONObject.class)));
		JSONObject object = (JSONObject)parseResult;
		assertThat(object.propertyNames().size(), is(0));
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenUnclosedJSONObjectIsGiven() {
		try {
			JSONParser.fromString(" {\"test\":\"test\"").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("Unclosed JSON object at (1, 2)"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void parse_mustThrowJSONFormatExceptionWhenInvalidFormatJSONObjectIsGiven() {
		try {
			JSONParser.fromString("{\"test\"}").parse();
		} catch (JSONFormatException ok) {
			assertThat(ok.getMessage(), is("JSON object key value delimeter ':' is expected, but actual '}' is given at (1, 8)"));
		} catch (Exception e) {
			fail();
		}
	}

}
