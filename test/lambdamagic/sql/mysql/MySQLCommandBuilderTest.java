package lambdamagic.sql.mysql;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLDatabase;

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
	
	

}
