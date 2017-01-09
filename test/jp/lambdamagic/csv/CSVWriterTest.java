package jp.lambdamagic.csv;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.csv.CSVDataSource;
import jp.lambdamagic.csv.CSVWriter;

public class CSVWriterTest {
	
	private String TEST_OUTPUT_FILE_PATH = "test/jp/lambdamagic/csv/test_output.csv";

	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void CSVWriter_mustThrowNullArgumentExceptionWhenNullWriterIsGiven() throws IOException {
		new CSVWriter((Writer)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void CSVWriter_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
		new CSVWriter((String)null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void CSVWriter_mustThrowNullArgumentExceptionWhenNullEncodingIsGiven() throws IOException {
		new CSVWriter(TEST_OUTPUT_FILE_PATH, null);
	}
	
	@SuppressWarnings("resource")
	@Test(expected=UnsupportedEncodingException.class)
	public void CSVWriter_mustThrowUnsupportedEncodingExceptionWhenUnsupportedEncodingIsGiven() throws IOException {
		new CSVWriter(TEST_OUTPUT_FILE_PATH, "unknownEncoding");
	}
	
	@Test
	public void write_writeStringsToFile() throws IOException {
		try(CSVWriter csvWriter = new CSVWriter(TEST_OUTPUT_FILE_PATH)) {
			csvWriter.write(Arrays.asList("test1", "test2", "test3"));
			csvWriter.write(Arrays.asList("\"test1\"", "test2", "test3"));
		}

		try(CSVDataSource csvDataSource = new CSVDataSource(TEST_OUTPUT_FILE_PATH)) {
			Optional<List<String>> maybeData = csvDataSource.readData();
			
			assertThat(maybeData.isPresent(), is(true));
			List<String> data = maybeData.get();
			assertThat(data, is(Arrays.asList("test1", "test2", "test3")));
			
			maybeData = csvDataSource.readData();
			assertThat(maybeData.isPresent(), is(true));
			data = maybeData.get();
			assertThat(data, is(Arrays.asList("\"test1\"", "test2", "test3")));
		}
	}	
	
}
