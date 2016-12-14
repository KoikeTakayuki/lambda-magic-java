package lambdamagic.sql;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLType;
import lambdamagic.test.AssertionMessage;

public class SQLTypeTest {

    public static void main(String[] args) {
        JUnitCore.main(SQLTypeTest.class.getName());
    }
	
	@Test
	public void constructor_failure_withNullName() {
		try {
			new SQLType(null);
			fail(AssertionMessage.expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(AssertionMessage.expectNullArgumentException(e.getClass()));
		}
		
		try {
			new SQLType(null, 1);
			fail(AssertionMessage.expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(AssertionMessage.expectNullArgumentException(e.getClass()));
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
			fail(AssertionMessage.notExpectException(e.getClass()));
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
			fail(AssertionMessage.notExpectException(e.getClass()));
		}
	}

}
