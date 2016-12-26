package lambdamagic.sql;

import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.SQLInsertQuery;
import lambdamagic.sql.query.SQLSelectQuery;
import lambdamagic.sql.query.SQLUpdateQuery;

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
	String buildInsertIntoCommand(SQLInsertQuery query);
	String buildLastInsertIdCommand();
	String buildUpdateCommand(SQLUpdateQuery query);
	String buildDeleteFromCommand(SQLDeleteQuery query);
	String buildSelectCommand(SQLSelectQuery query);
}