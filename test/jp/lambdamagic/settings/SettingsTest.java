package jp.lambdamagic.settings;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class SettingsTest {
	
	private static String TEST_DATA_FILE_PATH = "test/jp/lambdamagic/settings/test_input.properties";

	@Test(expected=NullArgumentException.class)
	public void load_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
		Settings.load((String)null);
	}
	
	@Test(expected=NullArgumentException.class)
	public void load_mustThrowNullArgumentExceptionWhenNullInputStreamIsGiven() throws IOException {
		Settings.load((InputStream)null);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void load_mustThrowFileNotFoundArgumentExceptionWhenFileNotFound() throws IOException {
		Settings.load("test");
	}
	
	@Test(expected=NullArgumentException.class)
	public void get_mustThrowNullArgumentExceptionWhenNullPropertyNameIsGiven() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		settings.get(null);
	}
	
	@Test
	public void get_returnEmptyIfPropertyValueNotExists() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);		

		Optional<String> test3 = settings.get("test3");
		assertThat(test3.isPresent(), is(false));
	}
	
	@Test
	public void get_returnPropertyValueIfPropertyValueExists() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		
		Optional<String> test1 = settings.get("test1");
		assertThat(test1.isPresent(), is(true));
		assertThat(test1.get(), is("100"));
		
		Optional<String> test2 = settings.get("test2");
		assertThat(test2.isPresent(), is(true));
		assertThat(test2.get(), is("test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void set_mustThrowNullArgumentExceptionWhenNullPropertyNameIsGiven() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		settings.set(null, "value");
	}
	
	@Test(expected=NullArgumentException.class)
	public void set_mustThrowNullArgumentExceptionWhenNullValueIsGiven() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		settings.set("key", null);
	}
	
	@Test
	public void set_setKeyValueToSettings() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		
		Optional<String> key = settings.get("key");
		assertThat(key.isPresent(), is(false));
		
		settings.set("key", "value");
		
		key = settings.get("key");
		assertThat(key.isPresent(), is(true));
		assertThat(key.get(), is("value"));
	}
	
	@Test
	public void propertyNames_returnPropertyNamesOfSettings() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		assertThat(settings.propertyNames(), hasItem("test1"));
		assertThat(settings.propertyNames(), hasItem("test2"));
		assertThat(settings.propertyNames(), not(hasItem("test3")));
	}
	
	@Test(expected=NullArgumentException.class)
	public void save_mustThrowNullArgumentExceptionWhenNullOutputStreamIsGiven() throws IOException {
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		settings.save(null);
	}
	
	@Test
	public void save_saveSettingsToOutputStream() throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		
		Settings settings = Settings.load(TEST_DATA_FILE_PATH);
		settings.set("key", "value");
		settings.save(os);
		
		String result = os.toString();
		assertThat(result, containsString("key=value"));
		assertThat(result, containsString("test1=100"));
		assertThat(result, containsString("test2=test"));
	}

}
