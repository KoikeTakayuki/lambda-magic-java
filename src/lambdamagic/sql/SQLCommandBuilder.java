package lambdamagic.sql;

import java.util.Map;

public interface SQLCommandBuilder {

	String buildCreateDatabaseCommand(SQLDatabase database);
	String buildDropDatabaseCommand(String databaseName);
	String buildUseDatabaseCommand(String databaseName);
	String buildTableExistsCommand(String tableName);
	String buildCreateTableCommand(SQLTable table);
	String buildRenameTableCommand(String tableName, String newTableName);
	String buildTableColumnExistsCommand(String tableName, String columnName);
	String buildAddTableColumnCommand(String tableName, String columnName, SQLType columnType);
	String buildDropTableColumnCommand(String tableName, String columnName);
	String buildDropTableCommand(String tableName);
	String buildInsertIntoCommand(String tableName, Map<String, ?> values);
	String buildLastInsertIdCommand();
	String buildUpdateCommand(String tableName, String columnName, Object value, Map<String, ?> values);
	String buildSelectCommand(SQLQuery query);
}
