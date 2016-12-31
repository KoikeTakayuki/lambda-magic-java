package lambdamagic.sql;

import lambdamagic.sql.SQLTable.Column;
import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.SQLInsertQuery;
import lambdamagic.sql.query.SQLSelectQuery;
import lambdamagic.sql.query.SQLUpdateQuery;

public interface SQLCommandBuilder {

	/* SQL Database Operation */
	String buildCreateDatabaseCommand(SQLDatabase database);
	String buildDropDatabaseCommand(String databaseName);
	String buildUseDatabaseCommand(String databaseName);
	
	/* SQL Table Operation */
	String buildTableExistsCommand(String tableName);
	String buildCreateTableCommand(SQLTable table);
	String buildRenameTableCommand(String tableName, String newTableName);
	String buildDropTableCommand(String tableName);

	/* SQL Table Column Operation */	
	String buildTableColumnExistsCommand(String tableName, String columnName);
	String buildAddTableColumnCommand(String tableName, Column column);
	String buildDropTableColumnCommand(String tableName, String columnName);

	/* SQL Table Row Operation */
	String buildInsertIntoCommand(SQLInsertQuery query);
	String buildSelectCommand(SQLSelectQuery query);
	String buildUpdateCommand(SQLUpdateQuery query);
	String buildDeleteFromCommand(SQLDeleteQuery query);
	String buildLastInsertIdCommand();
}