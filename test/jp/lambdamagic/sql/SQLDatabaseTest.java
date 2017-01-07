package jp.lambdamagic.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jp.lambdamagic.sql.SQLDatabase;

public class SQLDatabaseTest {

	@Test
	public void constructor_failure_withNullName() {

	}
	
	@Test
	public void constructor_success_withNullCollation() {
		try {
			new SQLDatabase("name", null);
		} catch (Exception e) {
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
		}
	}
}
