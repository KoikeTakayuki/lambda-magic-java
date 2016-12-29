package lambdamagic.sql.mysql;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLDatabase;
import lambdamagic.sql.SQLTable;
import lambdamagic.sql.SQLTable.Column;

public class MySQLCommandBuilderTest {

	@Test(expected=NullArgumentException.class)
	public void buildCreateDatabaseCommand_mustThrowNullArgumentExceptionWhenNullDatabaseIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildCreateDatabaseCommand(null);
	}
	
	@Test
	public void buildCreateDatabaseCommand_buildMySQLCreateDatabaseCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildCreateDatabaseCommand(new SQLDatabase("test"));
		
		assertThat(result, is("CREATE DATABASE test"));
	}
	
	@Test
	public void buildCreateDatabaseCommand_buildMySQLCreateDatabaseCommandWithCollation() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildCreateDatabaseCommand(new SQLDatabase("test", "utf8_general_ci"));
		
		assertThat(result, is("CREATE DATABASE test COLLATE utf8_general_ci"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildDropDatabaseCommand_mustThrowNullArgumentExceptionWhenNullDatabaseNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDropDatabaseCommand(null);
	}
	
	@Test
	public void buildDropDatabaseCommand_buildMySQLDropDatabaseCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildDropDatabaseCommand("test");
		
		assertThat(result, is("DROP DATABASE test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildUseDatabaseCommand_mustThrowNullArgumentExceptionWhenNullDatabaseNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildUseDatabaseCommand(null);
	}
	
	@Test
	public void buildUseDatabaseCommand_buildMySQLUseDatabaseCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildUseDatabaseCommand("test");

		assertThat(result, is("USE test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildTableExistsCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildTableExistsCommand(null);
	}
	
	@Test
	public void buildTableExistsCommand_buildMySQLCountSingleQuery() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildTableExistsCommand("test");

		assertThat(result, is("SELECT COUNT(*) FROM test LIMIT 1"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildCreateTableCommand_mustThrowNullArgumentExceptionWhenNullTableIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildCreateTableCommand(null);
	}
	
	@Test
	public void buildCreateTableCommand_buildMySQLCreateTableCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		List<Column> columns = new ArrayList<>();
		columns.add(new SQLTable.Column("id", MySQLCommandBuilder.INTEGER, MySQLCommandBuilder.PRIMARY_KEY, MySQLCommandBuilder.AUTO_INCREMENT, MySQLCommandBuilder.NOT_NULL));
		columns.add(new SQLTable.Column("name", MySQLCommandBuilder.TEXT));
		columns.add(new SQLTable.Column("password", MySQLCommandBuilder.PASSWORD));
		String result = commandBuilder.buildCreateTableCommand(new SQLTable("test", columns));

		assertThat(result, is("CREATE TABLE test (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(255), password CHAR(32))"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildRenameTableCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildRenameTableCommand(null, "newTableName");
	}

	@Test(expected=NullArgumentException.class)
	public void buildRenameTableCommand_mustThrowNullArgumentExceptionWhenNullNewTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildRenameTableCommand("test", null);
	}
	
	@Test
	public void buildRenameTableCommand_buildMySQLRenameTableCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildRenameTableCommand("test", "test2");
		
		assertThat(result, is("RENAME TABLE test TO test2"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildDropTableCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDropTableCommand(null);
	}
	
	@Test
	public void buildDropTableCommand_buildMySQLDropTableCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildDropTableCommand("test");
		
		assertThat(result, is("DROP TABLE test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildTableColumnExistsCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildTableColumnExistsCommand(null, "name");
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildTableColumnExistsCommand_mustThrowNullArgumentExceptionWhenNullColumnNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildTableColumnExistsCommand("test", null);
	}
	
	@Test
	public void buildTableColumnExistsCommand_buildMySQLSelectSingleQuery() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildTableColumnExistsCommand("test", "name");
		
		assertThat(result, is("SELECT name FROM test LIMIT 1"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildAddTableColumnCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildAddTableColumnCommand(null, new Column("name", MySQLCommandBuilder.TEXT));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildAddTableColumnCommand_mustThrowNullArgumentExceptionWhenColumnIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildAddTableColumnCommand("test", null);
	}
	
	@Test
	public void buildAddTableColumnCommand_() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildAddTableColumnCommand("test", new Column("name", MySQLCommandBuilder.TEXT));
		
		assertThat(result, is("ALTER TABLE test ADD name VARCHAR(255)"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildDropTableColumnCommand_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDropTableColumnCommand(null, "name");
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildDropTableColumnCommand_mustThrowNullArgumentExceptionWhenNullColumnNameIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDropTableColumnCommand("test", null);
	}
	
	@Test
	public void buildDropTableColumnCommand_buildMySQLDropTableColumnCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildDropTableColumnCommand("test", "name");
		
		assertThat(result, is("ALTER TABLE test DROP COLUMN name"));
	}
}
