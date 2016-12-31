package lambdamagic.sql.mysql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.collection.iterator.Iterables;
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
import lambdamagic.text.Strings;


public class MySQLCommandBuilder implements SQLCommandBuilder {
	
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
		
		appendColumnDefinitions(sb, table.getColumns());

		for (String declaration : table.getCustomDeclarations()) {
			sb.append(", ");
			sb.append(declaration);
		}
		sb.append(')');
		
		return sb.toString();
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
	public String buildDropTableCommand(String tableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		return Strings.concat("DROP TABLE ", tableName);
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
	public String buildAddTableColumnCommand(String tableName, Column column) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (column == null)
			throw new NullArgumentException("column");
		
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ADD ");
		appendColumnDefinitions(sb, Iterables.asIterable(column));

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
	public String buildSelectCommand(SQLSelectQuery query) {
		return null;
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

		return null;
	}

	@Override
	public String buildLastInsertIdCommand() {
		return "SELECT LAST_INSERT_ID()";
	}
	
	private void appendColumnDefinitions(StringBuffer sb, Iterable<Column> columns) {
		Iterator<Column> it = columns.iterator();
		List<Column> indexedColumns  = new ArrayList<>();

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

			if (constraint.equals(MySQLConstraint.INDEX)) {
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
	
	private String escapeString(String s) {
		return s.replaceAll(Pattern.quote("'"), "''").replaceAll(Pattern.quote("\\"), "\\\\");
	}
	
}
