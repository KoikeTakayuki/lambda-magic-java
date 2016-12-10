package lambdamagic.csv;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.pipeline.DataSource;
import lambdamagic.test.AssertionMessage;

public class CSVDataSourceTest {

	@SuppressWarnings("resource")
	@Test
	public void createWithNullArgument() {
		try {
			new CSVDataSource(null);
			fail(AssertionMessage.expectNullArgumentException());
		} catch (NullArgumentException e) {

		} catch(Exception e) {
			fail(AssertionMessage.expectNullArgumentException(e.getClass()));
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void createWithNotExistingFile() {
		try {
			new CSVDataSource("");
			fail(AssertionMessage.expectException(FileNotFoundException.class));
		} catch (FileNotFoundException e) {

		} catch(Exception e) {
			fail(AssertionMessage.expectException(FileNotFoundException.class, e.getClass()));
		}
	}
	
	@SuppressWarnings("resource")
	@Test
	public void create() {
		try {
			new CSVDataSource("test/lambdamagic/csv/testdata.csv");
		} catch(Exception e) {
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}
	
	@SuppressWarnings("resource")
	@Test
	public void read() {
		try {
			DataSource<List<String>> dataSource = new CSVDataSource("test/lambdamagic/csv/testdata.csv");
			Optional<List<String>> maybeData = dataSource.readData();
			
			if (maybeData.isPresent()) {
				List<String> data = maybeData.get();
				assertEquals("test1", data.get(0));
				assertEquals("test2", data.get(1));
				assertEquals("test3", data.get(2));
			} else {
				fail("not read correctly");
			}
			
			maybeData = dataSource.readData();
			if (maybeData.isPresent()) {
				List<String> data = maybeData.get();
				assertEquals("1", data.get(0));
				assertEquals("2", data.get(1));
				assertEquals("日本語のテスト", data.get(2));

			} else {
				fail("not read correctly");
			}
			
			maybeData = dataSource.readData();
			if (maybeData.isPresent()) {
				List<String> data = maybeData.get();
				assertTrue(data.isEmpty());
			} else {
				fail("not read correctly");
			}
			
			maybeData = dataSource.readData();
			if (maybeData.isPresent()) {
				List<String> data = maybeData.get();
				assertEquals("test1", data.get(0));
				assertEquals("\"test2\"", data.get(1));
				assertEquals("test3\\n", data.get(2));
				assertEquals("ok", data.get(3));
			} else {
				fail("not read correctly");
			}
			
			maybeData = dataSource.readData();
			if (maybeData.isPresent()) {
				fail("not read correctly");
			}

			dataSource.close();
		} catch(Exception e) {
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}
	
	

}
