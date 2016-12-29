package lambdamagic.sql;

import lambdamagic.sql.SQLTable.Column;
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
	String buildDropTableCommand(String tableName);
	String buildTableColumnExistsCommand(String tableName, String columnName);
	String buildAddTableColumnCommand(String tableName, Column column);
	String buildDropTableColumnCommand(String tableName, String columnName);
	String buildInsertIntoCommand(SQLInsertQuery query);
	String buildUpdateCommand(SQLUpdateQuery query);
	String buildDeleteFromCommand(SQLDeleteQuery query);
	String buildSelectCommand(SQLSelectQuery query);
	String buildLastInsertIdCommand();
}