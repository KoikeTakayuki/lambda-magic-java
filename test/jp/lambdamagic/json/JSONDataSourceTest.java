package jp.lambdamagic.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.JSONDataSource;
import jp.lambdamagic.json.JSONNull;
import jp.lambdamagic.pipeline.DataSource;

public class JSONDataSourceTest {
	
	@SuppressWarnings("resource")
	@Test
	public void constructor_failure_withNullString() {
		
		try {
			new JSONDataSource((String)null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void constructor_failure_withNullEncoding() {
		try (DataSource<Object> source = new JSONDataSource((Reader)null)) {
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}

	@Test
	public void constructor_failure_withNullReader() {
		try (DataSource<Object> source = new JSONDataSource((Reader)null)) {
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void fromString_failure_withNullString() {
		try (DataSource<Object> source = JSONDataSource.fromString(null)) {
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}

	@Test
	public void readData_failure_readInvalidJSON() {
		try {
			DataSource<Object> source = JSONDataSource.fromString("invalid json format");
			Optional<Object> maybeData = source.readData();
			assertTrue(!maybeData.isPresent());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readTrue() {
		try (DataSource<Object> source = JSONDataSource.fromString("true");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Boolean data = (Boolean) maybeData.get();
			assertTrue(data);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readFalse() {
		try (DataSource<Object> source = JSONDataSource.fromString("false");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Boolean data = (Boolean) maybeData.get();
			assertTrue(!data);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readNull() {
		try (DataSource<Object> source = JSONDataSource.fromString("null")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Object data = maybeData.get();
			assertTrue(data instanceof JSONNull);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readString() {
		try (DataSource<Object> source = JSONDataSource.fromString("\"testString\"")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			String data = (String)maybeData.get();
			assertEquals("testString", data);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readNegativeNumber() {
		try (DataSource<Object> source = JSONDataSource.fromString("-100");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Number data = (Number)maybeData.get();
			assertEquals(-100, data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readPositiveNumber() {
		try (DataSource<Object> source = JSONDataSource.fromString("100");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Number data = (Number)maybeData.get();
			assertEquals(100, data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readZero() {
		try (DataSource<Object> source = JSONDataSource.fromString("0");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Number data = (Number)maybeData.get();
			assertEquals(-0, data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void readData_success_readDecimalNumber() {
		try (DataSource<Object> source = JSONDataSource.fromString("0.123");) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Number data = (Number)maybeData.get();
			assertEquals(0.123, data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readUpperLimitNumber() {
		try (DataSource<Object> source = JSONDataSource.fromString("2147483647")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Number data = (Number)maybeData.get();
			assertEquals(Integer.MAX_VALUE, data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void readData_success_readNormalizedNumber() {
		try (
			DataSource<Object> source1 = JSONDataSource.fromString("1e7");
			DataSource<Object> source2 = JSONDataSource.fromString("1e+7");
			DataSource<Object> source3 = JSONDataSource.fromString("1e-7");
			DataSource<Object> source4 = JSONDataSource.fromString("1.01e-3");) {

			Optional<Object> maybeData1 = source1.readData();
			assertTrue(maybeData1.isPresent());
			Number data1 = (Number)maybeData1.get();
			assertEquals(1.0e7, data1);
			
			Optional<Object> maybeData2 = source2.readData();
			assertTrue(maybeData2.isPresent());
			Number data2 = (Number)maybeData2.get();
			assertEquals(1.0e7, data2);
			
			Optional<Object> maybeData3 = source3.readData();
			assertTrue(maybeData3.isPresent());
			Number data3 = (Number)maybeData3.get();
			assertEquals(1e-7, data3);
			
			Optional<Object> maybeData4 = source4.readData();
			assertTrue(maybeData4.isPresent());
			Number data4 = (Number)maybeData4.get();
			assertEquals(1.01e-3, data4);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void readData_success_readEmptyArray() {
		try (DataSource<Object> source = JSONDataSource.fromString("[]")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			List<Object> data = (List<Object>)maybeData.get();
			assertEquals(new ArrayList<Object>(), data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void readData_success_readEmptyObject() {
		try (DataSource<Object> source = JSONDataSource.fromString("{}")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Object data = maybeData.get();
			assertEquals(new Object(), data);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void readData_success_readObject() {
		try (DataSource<Object> source = JSONDataSource.fromString("{\"t\": -1.1e2, \"t2\":true, \"t3\" : [1, 2, 3], \"t4\"   :   \"test\" }")) {
			Optional<Object> maybeData = source.readData();

			assertTrue(maybeData.isPresent());
			Map<String, Object> data = (Map<String, Object>)maybeData.get();
			assertEquals(-110, (Number)data.get("t"));
			assertEquals(true, (Boolean)data.get("t2"));
			assertEquals(Arrays.asList(1, 2, 3), (List<Object>)data.get("t3"));
			assertEquals("test", (List<Object>)data.get("t4"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


}
