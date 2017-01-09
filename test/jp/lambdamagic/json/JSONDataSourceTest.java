package jp.lambdamagic.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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

public class JSONDataSourceTest {
	
	private String TEST_DATA_FILE_PATH = "test/jp/lambdamagic/json/test_input.json";

	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONDataSource_mustThrowNullArgumentExceptionWhenNullReaderIsGiven() throws IOException {
		new JSONDataSource((Reader)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONDataSource_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
		new JSONDataSource((String)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void JSONDataSource_mustThrowNullArgumentExceptionWhenNullEncodingIsGiven() throws IOException {
		new JSONDataSource("filePath", null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=FileNotFoundException.class)
	public void JSONDataSource_mustThrowFileNotFoundExceptionWhenNonExistingFilePathIsGiven() throws IOException {
		new JSONDataSource("filePath");
	}
	
	@SuppressWarnings("resource")
	@Test(expected=UnsupportedEncodingException.class)
	public void JSONDataSource_mustThrowUnsupportedEncodingExceptionWhenUnsupportedEncodingIsGiven() throws IOException {
		new JSONDataSource(TEST_DATA_FILE_PATH, "unknownEncoding");
	}
	
	@Test(expected=NullArgumentException.class)
	public void fromString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() throws IOException {
		JSONDataSource.fromString(null);
	}
	
	@Test
	public void readData_provideJSONData() throws IOException {
		JSONDataSource dataSource = new JSONDataSource(TEST_DATA_FILE_PATH);
		
		Optional<JSONData> data = dataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(instanceOf(JSONObject.class)));
		JSONObject object = (JSONObject)data.get();
		
		assertThat(object.get("test1").isPresent(), is(true));
		assertThat(object.get("test1").get(), is(instanceOf(JSONString.class)));
		JSONString jsonString = (JSONString)object.get("test1").get();
		assertThat(jsonString.getValue(), is("test"));
		
		assertThat(object.get("test2").isPresent(), is(true));
		assertThat(object.get("test2").get(), is(instanceOf(JSONBoolean.class)));
		JSONBoolean jsonBool = (JSONBoolean)object.get("test2").get();
		assertThat(jsonBool.getValue(), is(true));
		
		assertThat(object.get("test3").isPresent(), is(true));
		assertThat(object.get("test3").get(), is(instanceOf(JSONNumber.class)));
		JSONNumber jsonNumber1 = (JSONNumber)object.get("test3").get();
		assertThat(jsonNumber1.getValue(), is(1e+10));
		
		assertThat(object.get("test4").isPresent(), is(true));
		assertThat(object.get("test4").get(), is(instanceOf(JSONNumber.class)));
		JSONNumber jsonNumber2 = (JSONNumber)object.get("test4").get();
		assertThat(jsonNumber2.getValue(), is(0));
		
		assertThat(object.get("test5").isPresent(), is(true));
		assertThat(object.get("test5").get(), is(instanceOf(JSONNull.class)));
		
		assertThat(object.get("test6").isPresent(), is(true));
		assertThat(object.get("test6").get(), is(instanceOf(JSONObject.class)));
		JSONObject innerObject = (JSONObject)object.get("test6").get();
		
		assertThat(innerObject.get("test7").isPresent(), is(true));
		assertThat(innerObject.get("test7").get(), is(instanceOf(JSONArray.class)));
		JSONArray jsonArray1 = (JSONArray)innerObject.get("test7").get();
		assertThat(jsonArray1.size(), is(3));
		assertThat(jsonArray1.get(0), is(instanceOf(JSONString.class)));
		assertThat(((JSONString)jsonArray1.get(0)).getValue(), is("test"));
		assertThat(jsonArray1.get(1), is(instanceOf(JSONString.class)));
		assertThat(((JSONString)jsonArray1.get(1)).getValue(), is("test"));
		assertThat(jsonArray1.get(2), is(instanceOf(JSONString.class)));
		assertThat(((JSONString)jsonArray1.get(2)).getValue(), is("test"));

		assertThat(object.get("test8").isPresent(), is(true));
		assertThat(object.get("test8").get(), is(instanceOf(JSONArray.class)));
		JSONArray jsonArray2 = (JSONArray)object.get("test8").get();
		assertThat(jsonArray2.size(), is(1));
		assertThat(jsonArray2.get(0), is(instanceOf(JSONArray.class)));
		
		JSONArray innerArray1 = (JSONArray)jsonArray2.get(0);
		assertThat(innerArray1.size(), is(1));
		assertThat(innerArray1.get(0), is(instanceOf(JSONArray.class)));
		
		JSONArray innerArray2 = (JSONArray)innerArray1.get(0);
		assertThat(innerArray2.size(), is(0));
		
		assertThat(data.isPresent(), is(false));
		dataSource.close();
	}
	
	public void readData_provideJSONArrayElement() {
		
	}

}
