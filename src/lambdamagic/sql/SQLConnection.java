package lambdamagic.sql;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.SQLDeleteQueryBuilder;
import lambdamagic.sql.query.SQLInsertQuery;
import lambdamagic.sql.query.SQLInsertQueryBuilder;
import lambdamagic.sql.query.SQLSelectQuery;
import lambdamagic.sql.query.SQLUpdateQuery;
import lambdamagic.sql.query.SQLUpdateQueryBuilder;
import lambdamagic.sql.query.condition.SQLCondition;

public abstract class SQLConnection implements AutoCloseable {

	private Connection connection;
	private PrintStream debugOutput;
	private SQLCommandBuilder commandBuilder;
	
	protected SQLConnection(DataSource dataSource, SQLCommandBuilder commandBuilder) throws SQLException {
		if (dataSource == null) {
			throw new NullArgumentException("dataSource");
		}
		
		if (commandBuilder == null) {
			throw new NullArgumentException("commandBuilder");
		}
		
		this.connection = dataSource.getConnection();
		this.commandBuilder = commandBuilder;
	}

	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);
	}

	public Optional<PrintStream> getDebugOutput() {
		if (debugOutput == null) {
			return Optional.empty();
		}
		
		return Optional.of(debugOutput);
	}

	public void setDebugOutput(PrintStream debugOutput) {
		this.debugOutput = debugOutput;
	}
	
	public void commit() throws SQLException {
		connection.commit();
	}
	
	public void createDatabase(SQLDatabase database) throws SQLException {
		execute(commandBuilder.buildCreateDatabaseCommand(database));
	}
	
	public void dropDatabase(String databaseName) throws SQLException {
		execute(commandBuilder.buildDropDatabaseCommand(databaseName));
	}
	
	public void useDatabase(String databaseName) throws SQLException {
		execute(commandBuilder.buildUseDatabaseCommand(databaseName));
	}
	
	public boolean tableExists(String tableName) {
		try
		{
		  execute(commandBuilder.buildTableExistsCommand(tableName));
		  return true;
		}
		catch (SQLException ex) {
			return false;
		}
	}

	public void createTable(SQLTable table) throws SQLException {
		execute(commandBuilder.buildCreateTableCommand(table));
	}
	
	public void createTableIfNotExist(SQLTable table) throws SQLException {
		if (table == null) {
			throw new NullArgumentException("table");
		}
		
		if (!tableExists(table.getName())) {
			createTable(table);
		}
	}
	
	public void renameTable(String tableName, String newTableName) throws SQLException {
		execute(commandBuilder.buildRenameTableCommand(tableName, newTableName));
	}
	
	public boolean tableColumnExists(String tableName, String columnName) {
		try
		{
		  execute(commandBuilder.buildTableColumnExistsCommand(tableName, columnName));
		  return true;
		}
		catch (SQLException ex) {
			return false;
		}
	}
	
	public void addTableColumn(String tableName, SQLTable.Column column) throws SQLException {
		if (column == null)
			throw new NullArgumentException("column");
		
		execute(commandBuilder.buildAddTableColumnCommand(tableName, column));
	}
	
	public void dropTableColumn(String tableName, String columnName) throws SQLException {
		execute(commandBuilder.buildDropTableColumnCommand(tableName, columnName));
	}
	
	public void dropTable(String tableName) throws SQLException {
		execute(commandBuilder.buildDropTableCommand(tableName));
	}
	
	public int insertInto(String tableName, Map<String, Object> values) throws SQLException {
		SQLInsertQuery query = SQLInsertQueryBuilder.insertInto(tableName).values(values).build();
		String command = commandBuilder.buildInsertCommand(query);

		getDebugOutput().ifPresent(output -> output.println(replaceJokerValues(command, values.values())));
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
			int count = 0;
			
			for (Map.Entry<String, ?> e : values.entrySet()) {
				preparedStatement.setObject(++count, e.getValue());
			}
			
			preparedStatement.executeUpdate();
		}
		
		return executeGetLastInsertId();
	}
	
	public SQLResultSet select(SQLSelectQuery query) {
		String command = commandBuilder.buildSelectCommand(query);
		return new SQLResultSet(() -> executeQuery(command));
	}
	
	public int update(String tableName, SQLCondition condition, Map<String, Object> values) throws SQLException {
		SQLUpdateQuery query = SQLUpdateQueryBuilder.update(tableName).set(values).where(condition).build();
		String command = commandBuilder.buildUpdateCommand(query);
		
		if (debugOutput != null)
			debugOutput.println(replaceJokerValues(command, values.values()));
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
			int count = 0;
			for (Map.Entry<String, ?> e : values.entrySet())
				preparedStatement.setObject(++count, e.getValue());	
			
			preparedStatement.executeUpdate();
		}
		return executeGetLastInsertId();
	}
	
	public int update(String tableName, String columnName, Object value, Map<String, Object> values) throws SQLException {
		return update(tableName, SQLCondition.EQUAL_TO(columnName, value), values);
	}
	
	private String replaceJokerValues(String command, Collection<?> values) {
		StringBuffer sb = new StringBuffer();
		
		int startIndex = 0;
		int endIndex = 0;
		Iterator<?> it = values.iterator();
		
		while ((endIndex = command.indexOf('?', startIndex)) != -1) {
			sb.append(command.substring(startIndex, endIndex));
			sb.append("'");
			sb.append(String.valueOf(it.next()));
			sb.append("'");
			startIndex = endIndex + 1;
		}
		sb.append(command.substring(startIndex, command.length()));
		return sb.toString();
	}

	public int deleteFrom(String tableName, SQLCondition condition) throws SQLException {
		SQLDeleteQuery query = SQLDeleteQueryBuilder.deleteFrom(tableName).where(condition).build();
		return executeUpdate(commandBuilder.buildDeleteCommand(query));
	}
	
	@Override
	public void close() throws Exception {
		connection.close();
	}

	private int executeGetLastInsertId() throws SQLException {
		try (ResultSet resultSet = executeQuery(commandBuilder.buildLastInsertIdCommand())) {
			resultSet.next();
			return resultSet.getInt(1);
		}
	}

	private boolean execute(String command) throws SQLException {
		getDebugOutput().ifPresent(output -> output.println(command));
		
		try (Statement statement = connection.createStatement()) {
			return statement.execute(command);
		}
	}
	
	
	private int executeUpdate(String command) throws SQLException {
		getDebugOutput().ifPresent(output -> output.println(command));
	
		try (Statement statement = connection.createStatement()) {
			return statement.executeUpdate(command);
		}
	}
	
	private ResultSet executeQuery(String command) throws SQLException {
		getDebugOutput().ifPresent(output -> output.println(command));
		
		try (Statement statement = connection.createStatement()) {
			return connection.createStatement().executeQuery(command);
		}
	}
	
}
