package lambdamagic.sql;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import lambdamagic.NullArgumentException;

public class SQLTypeTest {

    public static void main(String[] args) {
        JUnitCore.main(SQLTypeTest.class.getName());
    }
	
	@Test
	public void constructor_failure_withNullName() {
		try {
			new SQLType(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
		
		try {
			new SQLType(null, 1);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void getName_success() {
		try {
			SQLType type = new SQLType("test");
			assertEquals("test", type.getName());
			
			SQLType type2 = new SQLType("test2", 2);
			assertEquals("test2", type2.getName());
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void getSize_success() {
		try {
			SQLType type = new SQLType("test");
			assertEquals(-1, type.getSize());
			
			SQLType type2 = new SQLType("test2", 2);
			assertEquals(2, type2.getSize());
			
		} catch (Exception e) {
		}
	}

}
