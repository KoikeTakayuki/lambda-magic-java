package lambdamagic.sql;

import static lambdamagic.test.AssertionMessage.*;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLTable;
import lambdamagic.sql.SQLType;
import lambdamagic.sql.SQLTable.Column;
import lambdamagic.sql.SQLTable.Column.Constraint;

public class SQLTableTest {
	
	@Test
	public void SQLTableTest_success() {
		try {
			SQLTable table = new SQLTable("name");
			SQLTable table2 = new SQLTable("name");
			assertEquals("name", table.getName());
			assertTrue(table.addColumn(new Column("column", new SQLType("TEXT"))));
			assertEquals(table, table2);
			table.addCustomDeclaration("test");

		} catch (Exception e) {
			fail(notExpectException(e.getClass()));
		}
	}
	
	
	@Test
	public void constructor_failure_withNullColumns() {
		try {
			new SQLTable("name", null);
			fail(expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(expectNullArgumentException(e.getClass()));
		}
	}


	@Test
	public void column_constructor_failure_withNullConstraints() {

		try {
			new SQLTable.Column("name", new SQLType("TEXT"), (Set<Constraint>)null);
			fail(expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(notExpectException(e.getClass()));
		}
		
	}
	
	@Test
	public void addColumn_failure_withNullColumn() {
		try {
			SQLTable table = new SQLTable("name");
			table.addColumn(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(expectNullArgumentException(e.getClass()));
		}
	}
	
	@Test
	public void addCustomDeclaration_failure_withNullCustomDeclaration() {
		try {
			SQLTable table = new SQLTable("name");
			table.addCustomDeclaration(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(expectNullArgumentException(e.getClass()));
		}
	}
	
	@Test
	public void constructor_failure_withNullName() {
		try {
			new SQLTable(null);
			fail(expectNullArgumentException());
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
			fail(expectNullArgumentException(e.getClass()));
		}
	}
	
	@Test
	public void SQLTable_Column_success() {

		try {
			
			Column column = new SQLTable.Column("name", new SQLType("TEXT"));
			
			assertEquals("name", column.getName());
			assertEquals("TEXT", column.getType().getName());
			assertNotNull(column.getConstraints());
			
		} catch (Exception e) {
			fail(notExpectException(e.getClass()));
		}
		
	}

	@Test
	public void SQLTable_Column_defaultConstraint_success() {

		try {
			Constraint defaultConstraint = SQLTable.Column.Constraint.DEFAULT("AUTO_INCREMENT");
			assertEquals("AUTO_INCREMENT", defaultConstraint.getValue());
			assertEquals("DEFAULT", defaultConstraint.getName());
		} catch (Exception e) {
			fail(notExpectException(e.getClass()));
		}
		
	}


}
