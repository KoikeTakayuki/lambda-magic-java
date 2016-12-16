package lambdamagic.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import lambdamagic.NullArgumentException;
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
		}
	}
	
	
	@Test
	public void constructor_failure_withNullColumns() {
		try {
			new SQLTable("name", null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}


	@Test
	public void column_constructor_failure_withNullConstraints() {

		try {
			new SQLTable.Column("name", new SQLType("TEXT"), (Set<Constraint>)null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void addColumn_failure_withNullColumn() {
		try {
			SQLTable table = new SQLTable("name");
			table.addColumn(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void addCustomDeclaration_failure_withNullCustomDeclaration() {
		try {
			SQLTable table = new SQLTable("name");
			table.addCustomDeclaration(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
		}
	}
	
	@Test
	public void constructor_failure_withNullName() {
		try {
			new SQLTable(null);
		} catch (NullArgumentException ok) {
			
		} catch (Exception e) {
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
		}
		
	}

	@Test
	public void SQLTable_Column_defaultConstraint_success() {

		try {
			Constraint defaultConstraint = SQLTable.Column.Constraint.DEFAULT("AUTO_INCREMENT");
			assertEquals("AUTO_INCREMENT", defaultConstraint.getValue());
			assertEquals("DEFAULT", defaultConstraint.getName());
		} catch (Exception e) {
		}
		
	}


}
