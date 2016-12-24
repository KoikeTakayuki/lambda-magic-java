package lambdamagic.sql;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.SQLInsertQuery;
import lambdamagic.sql.query.SQLQuery;


public abstract class SQLConnection<T extends SQLCommandBuilder> implements AutoCloseable {

	private Connection connection;
	private PrintStream debugOutput;
	private T commandBuilder;

	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);
	}

	public PrintStream getDebugOutput() {
		return debugOutput;
	}

	public void setDebugOutput(PrintStream debugOutput) {
		this.debugOutput = debugOutput;
	}

	protected SQLConnection(DataSource dataSource, T commandBuilder) throws SQLException {
		if (dataSource == null)
			throw new NullArgumentException("dataSource");
		
		if (commandBuilder == null)
			throw new NullArgumentException("commandBuilder");
		
		this.connection = dataSource.getConnection();
	}

	protected SQLConnection(String url, String userName, String password, T commandBuilder) throws SQLException {
		if (url == null)
			throw new NullArgumentException("url");
		
		if (commandBuilder == null)
			throw new NullArgumentException("commandBuilder");

		this.connection = openConnection(url, userName, password);
	}
	
	private Connection openConnection(String databaseUrl, String userName, String password) throws SQLException {
		Properties properties = new Properties();
		
		if (userName != null)
			properties.put("user", userName);
		
		if (password != null)
			properties.put("password", password);
		
		return DriverManager.getConnection(databaseUrl, properties);
	}
	
	@Override
	public void close() throws Exception {
		connection.close();
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
		if (table == null)
			throw new NullArgumentException("table");
		
		if (!tableExists(table.getName()))
			createTable(table);
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
		
		execute(commandBuilder.buildAddTableColumnCommand(tableName, column.getName(), column.getType()));
	}
	
	public void dropTableColumn(String tableName, String columnName) throws SQLException {
		execute(commandBuilder.buildDropTableColumnCommand(tableName, columnName));
	}
	
	public void dropTable(String tableName) throws SQLException {
		execute(commandBuilder.buildDropTableCommand(tableName));
	}
	
	public int insertInto(String tableName, Map<String, ?> values) throws SQLException {
		String command = commandBuilder.buildInsertIntoCommand(new SQLInsertQuery(tableName, values));
		
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
	
	public int update(String tableName, String columnName, Object value, Map<String, ?> values) throws SQLException {
		return 1;
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
	
	public <T> T executeSingle(SQLQuery query) throws SQLException {
		return null;
	}
	
	public SQLResultSet execute(SQLQuery query) {
		return new SQLResultSet();
	}

	private int executeGetLastInsertId() throws SQLException {
		ResultSet resultSet = null;
		try {
			resultSet = executeQuery(commandBuilder.buildLastInsertIdCommand());
			resultSet.next();
			return resultSet.getInt(1);
		}
		finally {
			if (resultSet != null)
				resultSet.getStatement().close();
		}
	}

	private boolean execute(String command) throws SQLException {
		
		if (debugOutput != null)
			debugOutput.println(command);
		
		try (Statement statement = connection.createStatement()) {
			return statement.execute(command);
		}
	}
	
	
	public int executeUpdate(String command) throws SQLException {
		if (debugOutput != null)
			debugOutput.println(command);
		
		try (Statement statement = connection.createStatement()) {
			return statement.executeUpdate(command);
		}
	}
	
	private ResultSet executeQuery(String command) throws SQLException {
		if (debugOutput != null)
			debugOutput.println(command);
		
		return connection.createStatement().executeQuery(command);
	}
}
