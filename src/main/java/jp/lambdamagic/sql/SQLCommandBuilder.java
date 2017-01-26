package jp.lambdamagic.sql;

import jp.lambdamagic.sql.SQLTable.Column;
import jp.lambdamagic.sql.query.SQLDeleteQuery;
import jp.lambdamagic.sql.query.SQLInsertQuery;
import jp.lambdamagic.sql.query.SQLSelectQuery;
import jp.lambdamagic.sql.query.SQLUpdateQuery;

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
	String buildInsertCommand(SQLInsertQuery query);
	String buildSelectCommand(SQLSelectQuery query);
	String buildUpdateCommand(SQLUpdateQuery query);
	String buildDeleteCommand(SQLDeleteQuery query);
	String buildLastInsertIdCommand();
}