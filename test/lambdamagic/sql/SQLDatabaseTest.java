package lambdamagic.sql;

import static org.junit.Assert.*;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLDatabase;
import lambdamagic.test.AssertionMessage;

public class SQLDatabaseTest {

	@Test
	public void constructor_failure_withNullName() {
		try {
			new SQLDatabase(null);
			fail(AssertionMessage.expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(AssertionMessage.expectNullArgumentException(e.getClass()));
		}
	}
	
	@Test
	public void constructor_success_withNullCollation() {
		try {
			new SQLDatabase("name", null);
		} catch (Exception e) {
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}

	@Test
	public void getName_success() {
		try {
			SQLDatabase type = new SQLDatabase("test");
			assertEquals("test", type.getName());
			
			SQLDatabase type2 = new SQLDatabase("test2", "collation");
			assertEquals("test2", type2.getName());

		} catch (Exception e) {
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}
	
	@Test
	public void getCollation_success() {
		try {
			SQLDatabase type = new SQLDatabase("test");
			assertTrue(null == type.getCollation());
			
			SQLDatabase type2 = new SQLDatabase("test2", "collation");
			assertEquals("collation", type2.getCollation());
			
		} catch (Exception e) {
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}
}
