package lambdamagic.sql.mysql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.sql.SQLCommandBuilder;
import lambdamagic.sql.SQLDatabase;
import lambdamagic.sql.SQLTable;
import lambdamagic.sql.SQLTable.Column;
import lambdamagic.sql.SQLTable.Column.Constraint;
import lambdamagic.sql.SQLType;
import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.SQLInsertQuery;
import lambdamagic.sql.query.SQLSelectQuery;
import lambdamagic.sql.query.SQLUpdateQuery;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;
import lambdamagic.text.Strings;


public class MySQLCommandBuilder implements SQLCommandBuilder {
	
	public static final Constraint PRIMARY_KEY = new Constraint("PRIMARY KEY");
	public static final Constraint AUTO_INCREMENT = new Constraint("AUTO_INCREMENT");
	public static final Constraint INDEX = new Constraint("INDEX");
	public static final Constraint UNIQUE = new Constraint("UNIQUE");
	public static final Constraint NOT_NULL = new Constraint("NOT NULL");
	
	private MySQLConditionVisitor visitor;
	
	public MySQLCommandBuilder() {
		this.visitor = new MySQLConditionVisitor();
	}

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
		
		return Strings.concat("DROP DATABASE ", databaseName);
	}

	@Override
	public String buildUseDatabaseCommand(String databaseName) {
		if (databaseName == null)
			throw new NullArgumentException("databaseName");
		
		return Strings.concat("USE ", databaseName);
	}

	@Override
	public String buildTableExistsCommand(String tableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		return Strings.concat("SELECT COUNT(*) FROM ", tableName, " LIMIT 1");
	}

	@Override
	public String buildCreateTableCommand(SQLTable table) {
		if (table == null)
			throw new NullArgumentException("table");
		
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ");
		sb.append(table.getName());
		sb.append(" (");
		
		List<Column> indexedColumns = new ArrayList<Column>();
		Iterator<Column> it = table.getColumns().iterator();
		if (it.hasNext()) {		
			appendColumnDefinition(sb, it.next(), indexedColumns);
			
			while(it.hasNext()) {
				sb.append(", ");
				appendColumnDefinition(sb, it.next(), indexedColumns);
			}
		}
		
		for (Column c : indexedColumns) {
			sb.append(", ");
			sb.append(" INDEX(");
			sb.append(c.getName());
			sb.append(")");
		}

		for (String declaration : table.getCustomDeclarations()) {
			sb.append(", ");
			sb.append(declaration);
		}
		sb.append(')');
		
		return sb.toString();
	}
	
	private void appendColumnDefinition(StringBuffer sb, Column column, List<Column> indexedColumns) {
		sb.append(column.getName());
		
		SQLType columnType = column.getType();
		sb.append(' ');
		sb.append(columnType.getName());
		
		if (columnType.getSize() != -1) {
			sb.append('(');
			sb.append(columnType.getSize());
			sb.append(')');
		}
		
		Iterator<Constraint> it = column.getConstraints().iterator();
		while (it.hasNext()) {
			Constraint constraint = it.next();

			if (constraint.equals(INDEX)) {
				indexedColumns.add(column);
				return;
			}
			
			sb.append(' ');
			appendConstraintDefinition(sb, constraint);
		}
	}
	
	private void appendConstraintDefinition(StringBuffer sb, Column.Constraint constraint) {
		sb.append(constraint.getName());
		
		Object value = constraint.getValue();
		if (value != null) {
			sb.append(" '");
			
			if (value instanceof String)
				sb.append(escapeString((String)value));
			else
				sb.append(value);
			
			sb.append('\'');
		}
	}
	
	@Override
	public String buildRenameTableCommand(String tableName, String newTableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (newTableName == null)
			throw new NullArgumentException("newTableName");
		
		return Strings.concat("RENAME TABLE ", tableName, " TO ", newTableName);
	}
	
	@Override
	public String buildTableColumnExistsCommand(String tableName, String columnName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (columnName == null)
			throw new NullArgumentException("columnName");
		
		return Strings.concat("SELECT ", columnName, " FROM ", tableName, " LIMIT 1");
	}


	@Override
	public String buildAddTableColumnCommand(String tableName, String columnName, SQLType columnType) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (columnName == null)
			throw new NullArgumentException("columnName");
		
		if (columnType == null)
			throw new NullArgumentException("columnType");
		
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ADD ");
		sb.append(columnName);
		sb.append(' ');
		sb.append(columnType.getName());
		
		if (columnType.getSize() != -1) {
			sb.append('(');
			sb.append(columnType.getSize());
			sb.append(')');
		}

		return sb.toString();
	}

	@Override
	public String buildDropTableColumnCommand(String tableName, String columnName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (columnName == null)
			throw new NullArgumentException("columnName");
		
		return Strings.concat("ALTER TABLE ", tableName, " DROP COLUMN ", columnName);
	}

	@Override
	public String buildDropTableCommand(String tableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		return Strings.concat("DROP TABLE ", tableName);
	}

	@Override
	public String buildLastInsertIdCommand() {
		return "SELECT LAST_INSERT_ID()";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String buildInsertIntoCommand(SQLInsertQuery query) {
		if (query == null)
			throw new NullArgumentException("query");
		
		Map<String, ?> values = query.getValues();
	
		Iterator<Map.Entry<String, ?>> it = (Iterator<Map.Entry<String, ?>>)(Iterator<?>)values.entrySet().iterator();
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(query.getTableName());
		sb.append(" (");
		
		if (it.hasNext()) {
			sb.append(it.next().getKey());
			
			while (it.hasNext()) {
				sb.append(", ");
				sb.append(it.next().getKey());
			}
		}
		sb.append(") VALUES (");

		it = (Iterator<Map.Entry<String, ?>>)(Iterator<?>)values.entrySet().iterator();
		
		if (it.hasNext()) {
			it.next();
			sb.append('?');

			while (it.hasNext()) {
				it.next();
				sb.append(", ?");
			}
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public String buildUpdateCommand(SQLUpdateQuery query) {
		if (query == null)
			throw new NullArgumentException("query");
	
		Iterator<Map.Entry<String, Object>> it = query.getUpdateValues().entrySet().iterator();

		if (!it.hasNext())
			throw new OutOfRangeArgumentException("updateValues", "!it.hasNext()");
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append(query.getTableName());
		sb.append(" SET ");
		
		Map.Entry<String, ?> entry = it.next();
		sb.append(entry.getKey());
		sb.append(" = ?");
		
		while (it.hasNext()) {
			entry = it.next();
			sb.append(", ");
			sb.append(entry.getKey());
			sb.append(" = ?");
		}

		return sb.toString();
	}

	@Override
	public String buildDeleteFromCommand(SQLDeleteQuery query) {
		if (query == null)
			throw new NullArgumentException("query");
		
		String tableName = query.getTableName();
		List<SQLJoinClause> joinClauses = query.getJoinClauses();
		SQLCondition condition = query.getCondition();
		
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		sb.append(tableName);
		
		if (joinClauses.size() > 0) {
			
		}
		
		if (condition != null) {
			sb.append(" WHERE ");
			sb.append(condition.accept(visitor));
		}

		return sb.toString();
	}

	@Override
	public String buildSelectCommand(SQLSelectQuery query) {
		return null;
	}
	
	private String escapeString(String s) {
		return s.replaceAll(Pattern.quote("'"), "''").replaceAll(Pattern.quote("\\"), "\\\\");
	}
	
}
