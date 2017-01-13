package jp.lambdamagic.json;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;

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
	public synchronized void write_writeJSONToFile() throws Exception {
		StringWriter sw = new StringWriter();
		try(JSONWriter JSONWriter = new JSONWriter(sw)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.set("test1", new JSONNumber(100));
			jsonObject.set("test2", new JSONBoolean(true));
			JSONWriter.write(jsonObject);
			
			System.out.println(sw.toString());
		}

		try(JSONDataSource JSONDataSource = new JSONDataSource(TEST_OUTPUT_FILE_PATH)) {
			Optional<JSONData> maybeData = JSONDataSource.readData();
			
			assertThat(maybeData.isPresent(), is(true));
			assertThat(maybeData.get(), is(instanceOf(JSONObject.class)));
			JSONObject data = (JSONObject)maybeData.get();
			
			assertThat(data.get("test1").isPresent(), is(true));
			assertThat(data.get("test1").get(), is(instanceOf(JSONNumber.class)));
			assertThat(((JSONNumber)data.get("test1").get()).getValue(), is(100.0));
			
			assertThat(data.get("test2").isPresent(), is(true));
			assertThat(data.get("test2").get(), is(instanceOf(JSONBoolean.class)));
			assertThat(((JSONBoolean)data.get("test1").get()).getValue(), is(true));
		}
	}	

}
