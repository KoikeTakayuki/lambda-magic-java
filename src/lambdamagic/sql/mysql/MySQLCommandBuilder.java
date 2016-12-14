package lambdamagic.sql.mysql;

import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.SQLCommandBuilder;
import lambdamagic.sql.SQLDatabase;
import lambdamagic.sql.SQLQuery;
import lambdamagic.sql.SQLTable;
import lambdamagic.sql.SQLType;


public class MySQLCommandBuilder implements SQLCommandBuilder {

	@Override
	public String buildCreateDatabaseCommand(SQLDatabase database) {
		if (database == null)
			throw new NullArgumentException("database");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("CREATE DATABASE ");
		sb.append(database.getName());
		
		if (database.getCollation() != null) {
			sb.append(" COLLATE ");
			sb.append(database.getCollation());
		}
		return sb.toString();
	}

	@Override
	public String buildDropDatabaseCommand(String databaseName) {
		if (databaseName == null)
			throw new NullArgumentException("databaseName");
		
		return "DROP DATABASE " + databaseName;
	}

	@Override
	public String buildUseDatabaseCommand(String databaseName) {
		if (databaseName == null)
			throw new NullArgumentException("databaseName");
		
		return "USE " + databaseName;
	}

	@Override
	public String buildTableExistsCommand(String tableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		return "SELECT COUNT(*) FROM " + tableName + " LIMIT 1";
	}

	@Override
	public String buildCreateTableCommand(SQLTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildRenameTableCommand(String tableName, String newTableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildTableColumnExistsCommand(String tableName, String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildAddTableColumnCommand(String tableName, String columnName, SQLType columnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildDropTableColumnCommand(String tableName, String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildDropTableCommand(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildInsertIntoCommand(String tableName, Map<String, ?> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildLastInsertIdCommand() {
		return "SELECT LAST_INSERT_ID()";
	}

	@Override
	public String buildUpdateCommand(String tableName, String columnName, Object value, Map<String, ?> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildSelectCommand(SQLQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
