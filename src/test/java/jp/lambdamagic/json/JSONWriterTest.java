package jp.lambdamagic.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;

public class JSONWriterTest {

	private String TEST_OUTPUT_FILE_PATH = "test/jp/lambdamagic/json/test_output.json";

	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONWriter_mustThrowNullArgumentExceptionWhenNullWriterIsGiven() throws IOException {
		new JSONWriter((Writer)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONWriter_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
		new JSONWriter((String)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONWriter_mustThrowNullArgumentExceptionWhenNullEncodingIsGiven() throws IOException {
		new JSONWriter(TEST_OUTPUT_FILE_PATH, null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=UnsupportedEncodingException.class)
	public void JSONWriter_mustThrowUnsupportedEncodingExceptionWhenUnsupportedEncodingIsGiven() throws IOException {
		new JSONWriter(TEST_OUTPUT_FILE_PATH, "unknownEncoding");
	}
	
	@Test
	public void write_writeJSONNull() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONNull());
		}
		
		String result = writer.toString();
		assertThat(result, is("null"));
	}
	
	@Test
	public void write_writeJSONBoolean() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONBoolean(true));
		}
		
		String result = writer.toString();
		assertThat(result, is("true"));
	}
	
	@Test
	public void write_writeJSONString() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONString("test"));
		}
		
		String result = writer.toString();
		assertThat(result, is("\"test\""));
	}
	
	@Test
	public void write_writeJSONNumber() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONNumber(100));
		}
		
		String result = writer.toString();
		assertThat(result, is("100"));
	}
	
	@Test
	public void write_writeEmptyJSONArray() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONArray());
		}
		
		String result = writer.toString();
		assertThat(result, is("[]"));
	}
	
	@Test
	public void write_writeJSONArray() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(new JSONBoolean(true));
			jsonArray.add(new JSONBoolean(false));
			jsonArray.add(new JSONString("test"));
			jsonWriter.write(jsonArray);
		}
		
		String result = writer.toString();
		assertThat(result, is("[true,false,\"test\"]"));
	}
	
	@Test
	public void write_writeJSONObject() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.set("test1", new JSONBoolean(true));
			jsonObject.set("test2", new JSONBoolean(false));
			jsonObject.set("test3", new JSONString("test"));
			jsonWriter.write(jsonObject);
		}
		
		String result = writer.toString();
		assertThat(result, is("{\"test1\":true,\"test2\":false,\"test3\":\"test\"}"));
	}
	
	
	@Test
	public void write_writeEmptyJSONObject() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.write(new JSONObject());
		}
		
		String result = writer.toString();
		assertThat(result, is("{}"));
	}
	
	@Test
	public synchronized void write_writeJSONTOFile() throws Exception {
		Writer writer = new FileWriter(TEST_OUTPUT_FILE_PATH);
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.set("test1", new JSONNumber(100));
			jsonObject.set("test2", new JSONBoolean(true));
			jsonWriter.write(jsonObject);
		}

		try(JSONDataSource jsonDataSource = new JSONDataSource(TEST_OUTPUT_FILE_PATH)) {
			Optional<JSONData> maybeData = jsonDataSource.readData();
			
			assertThat(maybeData.isPresent(), is(true));
			assertThat(maybeData.get(), is(instanceOf(JSONObject.class)));
			JSONObject data = (JSONObject)maybeData.get();
			
			assertThat(data.get("test1").isPresent(), is(true));
			assertThat(data.get("test1").get(), is(instanceOf(JSONNumber.class)));
			assertThat(((JSONNumber)data.get("test1").get()).getValue(), is(100.0));
			
			assertThat(data.get("test2").isPresent(), is(true));
			assertThat(data.get("test2").get(), is(instanceOf(JSONBoolean.class)));
			assertThat(((JSONBoolean)data.get("test2").get()).getValue(), is(true));
		}
	}
	
	@Test
	public void write_writeAsArray() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			jsonWriter.writeAsArray();
			jsonWriter.write(new JSONString("test"));
			jsonWriter.write(new JSONArray());
		}
		
		String result = writer.toString();
		assertThat(result, is("[\"test\",[]]"));
	}
	
	@Test
	public void write_writeJSONStringIncludingSpecialCharacter() throws Exception {
		Writer writer = new StringWriter();
		
		try(JSONWriter jsonWriter = new JSONWriter(writer)) {
			JSONString jsonString = new JSONString("test\r\n");
			jsonWriter.write(jsonString);
		}
		
		String result = writer.toString();
		assertThat(result, is("\"test\\r\\n\""));

		try(JSONDataSource jsonDataSource = JSONDataSource.fromString(result)) {
			Optional<JSONData> maybeData = jsonDataSource.readData();
			
			assertThat(maybeData.isPresent(), is(true));
			assertThat(maybeData.get(), is(instanceOf(JSONString.class)));
			JSONString data = (JSONString)maybeData.get();
			assertThat(data.getValue(), is("test\r\n"));
		}
	}

}
