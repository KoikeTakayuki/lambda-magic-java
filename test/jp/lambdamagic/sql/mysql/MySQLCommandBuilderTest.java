package jp.lambdamagic.sql.mysql;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.SQLDatabase;
import jp.lambdamagic.sql.SQLTable;
import jp.lambdamagic.sql.SQLTable.Column;
import jp.lambdamagic.sql.mysql.MySQLCommandBuilder;
import jp.lambdamagic.sql.mysql.MySQLConstraint;
import jp.lambdamagic.sql.mysql.MySQLType;
import jp.lambdamagic.sql.query.SQLDeleteQuery;
import jp.lambdamagic.sql.query.SQLDeleteQueryBuilder;
import jp.lambdamagic.sql.query.SQLInsertQuery;
import jp.lambdamagic.sql.query.SQLInsertQueryBuilder;
import jp.lambdamagic.sql.query.SQLSelectQuery;
import jp.lambdamagic.sql.query.SQLSelectQueryBuilder;
import jp.lambdamagic.sql.query.SQLUpdateQuery;
import jp.lambdamagic.sql.query.SQLUpdateQueryBuilder;
import jp.lambdamagic.sql.query.condition.SQLCondition;

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
	public void buildTableExistsCommand_buildMySQLCountSingleCommand() {
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
		columns.add(new SQLTable.Column("id", MySQLType.INTEGER, MySQLConstraint.PRIMARY_KEY, MySQLConstraint.AUTO_INCREMENT, MySQLConstraint.NOT_NULL));
		columns.add(new SQLTable.Column("name", MySQLType.TEXT));
		columns.add(new SQLTable.Column("password", MySQLType.PASSWORD));
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
		commandBuilder.buildAddTableColumnCommand(null, new Column("name", MySQLType.TEXT));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildAddTableColumnCommand_mustThrowNullArgumentExceptionWhenColumnIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildAddTableColumnCommand("test", null);
	}
	
	@Test
	public void buildAddTableColumnCommand_buildMySQLAlterTableCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildAddTableColumnCommand("test", new Column("name", MySQLType.TEXT));
		
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
	
	@Test(expected=NullArgumentException.class)
	public void buildInsertCommand_mustThrowNullArgumentExceptionWhenNullQueryIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildInsertCommand(null);
	}
	
	@Test
	public void buildInsertCommand_buildMySQLInsertCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		LinkedHashMap<String, Object> insertValues = new LinkedHashMap<>();
		insertValues.put("id", 1);
		insertValues.put("name", "test");

		SQLInsertQuery query = SQLInsertQueryBuilder.insertInto("test").values(insertValues).build();
		String result = commandBuilder.buildInsertCommand(query);
		
		assertThat(result, is("INSERT INTO test (id, name) VALUES (?, ?)"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildSelectCommand_mustThrowExceptionWhenNullQueryIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildSelectCommand(null);
	}
	
	@Test
	public void buildSelectCommand_buildMySQLSelectCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLSelectQuery query = SQLSelectQueryBuilder.from("test")
													.select("id", "name")
													.build();
		String result = commandBuilder.buildSelectCommand(query);
		
		assertThat(result, is("SELECT id, name FROM test"));
	}
	
	@Test
	public void buildSelectCommand_buildMySQLSelectCommandWithJoinClause() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLSelectQuery query = SQLSelectQueryBuilder.from("test")
													.select("id", "name")
													.rightJoin("other_table", SQLCondition.equalTo("test.id", "other_table.test_id"))
													.where(SQLCondition.contain("other_table.name", "test"))
													.orderBy("test.name", true)
													.build();

		String result = commandBuilder.buildSelectCommand(query);
		
		assertThat(result, is("SELECT id, name FROM test RIGHT JOIN other_table ON test.id = other_table.test_id WHERE other_table.name LIKE '%test%' ORDER BY test.name ASC"));
	}
	
	@Test
	public void buildSelectCommand_buildMySQLSelectCommandWithCondition() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLSelectQuery query = SQLSelectQueryBuilder.from("test")
													.select("id", "name")
													.where(SQLCondition.equalTo("name", "test"))
													.orderBy("name", true)
													.build();

		String result = commandBuilder.buildSelectCommand(query);
		
		assertThat(result, is("SELECT id, name FROM test WHERE name = test ORDER BY name ASC"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildUpdateCommand_mustThrowExceptionWhenNullQueryIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDeleteCommand(null);
	}
	
	@Test
	public void buildUpdateCommand_buildMySQLUpdateCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		LinkedHashMap<String, Object> updateValues = new LinkedHashMap<>();
		updateValues.put("id", 1);
		updateValues.put("name", "test");

		SQLUpdateQuery query = SQLUpdateQueryBuilder.update("test")
													.set(updateValues)
													.build();
		String result = commandBuilder.buildUpdateCommand(query);
		
		assertThat(result, is("UPDATE test SET id = ?, name = ?"));
	}
	
	@Test
	public void buildUpdateCommand_buildMySQLUpdateCommandWithJoinClause() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		LinkedHashMap<String, Object> updateValues = new LinkedHashMap<>();
		updateValues.put("id", 1);
		updateValues.put("name", "test");
		SQLUpdateQuery query = SQLUpdateQueryBuilder.update("test")
													.set(updateValues)
													.leftJoin("other_table", SQLCondition.equalTo("test.id", "other_table.test_id"))
													.where(SQLCondition.greaterOrEqualTo("other_table.id", 3))
													.build();
		String result = commandBuilder.buildUpdateCommand(query);
		
		assertThat(result, is("UPDATE test SET id = ?, name = ? LEFT JOIN other_table ON test.id = other_table.test_id WHERE other_table.id >= 3"));
	}
	
	@Test
	public void buildUpdateCommand_buildMySQLUpdateCommandWithCondition() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		LinkedHashMap<String, Object> updateValues = new LinkedHashMap<>();
		updateValues.put("id", 1);
		updateValues.put("name", "test");

		SQLUpdateQuery query = SQLUpdateQueryBuilder.update("test")
													.set(updateValues)
													.where(SQLCondition.greaterThan("id", 3))
													.build();
		String result = commandBuilder.buildUpdateCommand(query);
		
		assertThat(result, is("UPDATE test SET id = ?, name = ? WHERE id > 3"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void buildDeleteCommand_mustThrowExceptionWhenNullQueryIsGiven() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		commandBuilder.buildDeleteCommand(null);
	}
	
	@Test
	public void buildDeleteCommand_buildMySQLDeleteCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom("test").build();
		String result = commandBuilder.buildDeleteCommand(query);
		
		assertThat(result, is("DELETE FROM test"));
	}
	
	@Test
	public void buildDeleteCommand_buildMySQLDeleteCommandWithJoinClause() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom("test")
													.innerJoin("other_table", SQLCondition.equalTo("test.id", "other_table.test_id"))
													.build();
		String result = commandBuilder.buildDeleteCommand(query);
		
		assertThat(result, is("DELETE FROM test INNER JOIN other_table ON test.id = other_table.test_id"));
	}
	
	@Test
	public void buildDeleteCommand_buildMySQLDeleteCommandWithCondition() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom("test")
													.where(SQLCondition.endWith("name", "a"))
													.build();
		String result = commandBuilder.buildDeleteCommand(query);
		
		assertThat(result, is("DELETE FROM test WHERE name LIKE '%a'"));
	}

	@Test
	public void buildLastInsertIdCommand_buildMySQLLastInsertCommand() {
		MySQLCommandBuilder commandBuilder = new MySQLCommandBuilder();
		String result = commandBuilder.buildLastInsertIdCommand();
		
		assertThat(result, is("SELECT LAST_INSERT_ID()"));
	}
	
}
