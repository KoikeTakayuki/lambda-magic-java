package jp.lambdamagic.sql.mysql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;
import jp.lambdamagic.sql.SQLCommandBuilder;
import jp.lambdamagic.sql.SQLDatabase;
import jp.lambdamagic.sql.SQLTable;
import jp.lambdamagic.sql.SQLType;
import jp.lambdamagic.sql.SQLTable.Column;
import jp.lambdamagic.sql.SQLTable.Column.Constraint;
import jp.lambdamagic.sql.query.SQLConditionalQuery;
import jp.lambdamagic.sql.query.SQLDeleteQuery;
import jp.lambdamagic.sql.query.SQLInsertQuery;
import jp.lambdamagic.sql.query.SQLSelectQuery;
import jp.lambdamagic.sql.query.SQLUpdateQuery;
import jp.lambdamagic.sql.query.condition.SQLConditionVisitor;
import jp.lambdamagic.sql.query.condition.SQLJoinClause;
import jp.lambdamagic.sql.query.condition.SQLJoinClause.JoinType;
import jp.lambdamagic.text.Strings;


public class MySQLCommandBuilder implements SQLCommandBuilder {
	
	public SQLConditionVisitor getConditionVisitor() {
		return new MySQLConditionVisitor();
	}
	
	@Override
	public String buildCreateDatabaseCommand(SQLDatabase database) {
		if (database == null) {
			throw new NullArgumentException("database");
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("CREATE DATABASE ");
		sb.append(database.getName());
		
		database.getCollation().ifPresent(collation -> {
			sb.append(" COLLATE ");
			sb.append(collation);
		});
		
		return sb.toString();
	}

	@Override
	public String buildDropDatabaseCommand(String databaseName) {
		if (databaseName == null) {
			throw new NullArgumentException("databaseName");
		}
		
		return Strings.concat("DROP DATABASE ", databaseName);
	}

	@Override
	public String buildUseDatabaseCommand(String databaseName) {
		if (databaseName == null) {
			throw new NullArgumentException("databaseName");
		}
		
		return Strings.concat("USE ", databaseName);
	}

	@Override
	public String buildTableExistsCommand(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		return Strings.concat("SELECT COUNT(*) FROM ", tableName, " LIMIT 1");
	}

	@Override
	public String buildCreateTableCommand(SQLTable table) {
		if (table == null) {
			throw new NullArgumentException("table");
		}
		
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
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (newTableName == null) {
			throw new NullArgumentException("newTableName");
		}
		
		return Strings.concat("RENAME TABLE ", tableName, " TO ", newTableName);
	}

	@Override
	public String buildDropTableCommand(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		return Strings.concat("DROP TABLE ", tableName);
	}
	
	@Override
	public String buildTableColumnExistsCommand(String tableName, String columnName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (columnName == null) {
			throw new NullArgumentException("columnName");
		}
		
		return Strings.concat("SELECT ", columnName, " FROM ", tableName, " LIMIT 1");
	}


	@Override
	public String buildAddTableColumnCommand(String tableName, Column column) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (column == null) {
			throw new NullArgumentException("column");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ADD ");
		appendColumnDefinitions(sb, Iterables.asIterable(column));

		return sb.toString();
	}

	@Override
	public String buildDropTableColumnCommand(String tableName, String columnName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (columnName == null) {
			throw new NullArgumentException("columnName");
		}
		
		return Strings.concat("ALTER TABLE ", tableName, " DROP COLUMN ", columnName);
	}

	@Override
	public String buildInsertCommand(SQLInsertQuery query) {
		if (query == null) {
			throw new NullArgumentException("query");
		}
		
		Map<String, Object> values = query.getValues();
		int valueCount = 0;
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(query.getTableName());
		sb.append(" (");
		
		for (Entry<String, Object> entry : values.entrySet()) {
			if (valueCount > 0) {
				sb.append(", ");
			}
			sb.append(entry.getKey());
			++valueCount;
		}
		
		sb.append(") VALUES (");
		
		for (int i = 0; i < valueCount; ++i) {
			if (i > 0) {
				sb.append(", ");
			}

			sb.append('?');
		}

		sb.append(')');
		return sb.toString();
	}

	@Override
	public String buildSelectCommand(SQLSelectQuery query) {
		if (query == null) {
			throw new NullArgumentException("query");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		
		if (query.isCount()) {
			sb.append("COUNT(");
		}
		
		if (query.isDistinct()) {
			sb.append("DISTINCT ");
		}
		
		if (!query.getSelectColumnNames().isPresent()) {
			sb.append("*");
		}
		
		query.getSelectColumnNames().ifPresent(columnNames -> {
			Iterator<String> selectIterator = columnNames.iterator();

			if (!selectIterator.hasNext()) {
				throw new IllegalStateException("columnNames should have at least one value");
			}
	
			sb.append(selectIterator.next());
			
			while (selectIterator.hasNext()) {
				sb.append(", ");
				sb.append(selectIterator.next());
			}
		});
		
		if (query.isCount()) {
			sb.append(")");
		}
		
		sb.append(" FROM ");
		sb.append(query.getTableName());
		
		appendJoinClause(sb, query);
		appendCondition(sb, query);
		
		query.getGroupByColumnNames().ifPresent(groupByColumnNames -> {
			
			Iterator<String> groupByIterator = groupByColumnNames.iterator();
			if (groupByIterator.hasNext()) {
				sb.append(" GROUP BY ");
				
				sb.append(groupByIterator.next());
				
				while (groupByIterator.hasNext()) {
					sb.append(", ");
					sb.append(groupByIterator.next());
				}
			}
		});
		
		query.getOrderColumnMap().ifPresent(orderColumnMap -> {
			for (Entry<String, Boolean> entry : orderColumnMap.entrySet()) {
				sb.append(" ORDER BY ");
				sb.append(entry.getKey());
				sb.append(' ');
				sb.append(entry.getValue() ? "ASC" : "DESC");
			}
		});
		
		if (query.getTakeCount() != -1) {
			sb.append(" LIMIT ");
			sb.append(query.getTakeCount());
		}
		
		if (query.getSkipCount() != -1) {
			if (query.getTakeCount() == -1) {
				sb.append(" LIMIT 18446744073709551615");
			}
			
			sb.append(" OFFSET ");
			sb.append(query.getSkipCount());
		}
		return sb.toString();
	}

	@Override
	public String buildUpdateCommand(SQLUpdateQuery query) {
		if (query == null) {
			throw new NullArgumentException("query");
		}
	
		Iterator<Map.Entry<String, Object>> it = query.getUpdateValues().entrySet().iterator();

		if (!it.hasNext()) {
			throw new IllegalStateException("updateValues should have at least one value");
		}
		
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
		
		appendJoinClause(sb, query);
		appendCondition(sb, query);

		return sb.toString();
	}

	@Override
	public String buildDeleteCommand(SQLDeleteQuery query) {
		if (query == null) {
			throw new NullArgumentException("query");
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM ");
		sb.append(query.getTableName());
		appendJoinClause(sb, query);
		appendCondition(sb, query);

		return sb.toString();
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
			
			if (value instanceof String) {
				sb.append(escapeString((String)value));
			} else {
				sb.append(value);
			}
			
			sb.append('\'');
		}
	}
	
	private String getJoinTypeString(JoinType joinType) {
		switch(joinType) {
			case INNER:
				return "INNER";
				
			case LEFT:
				return "LEFT";
				
			case RIGHT:
				return "RIGHT";
		}
		
		throw new UnsupportedOperationException("Unknown join type");
	}
	
	private void appendJoinClause(StringBuffer sb, SQLConditionalQuery query) {
		query.getJoinClauses().ifPresent(joinClauses -> {
			
			for (SQLJoinClause clause : joinClauses) {
				sb.append(' ');
				sb.append(getJoinTypeString(clause.getJoinType()));
				sb.append(" JOIN ");
				sb.append(clause.getTableName());
				sb.append(" ON ");
				sb.append(clause.getCondition().accept(getConditionVisitor()));
			}
		});

	}
	
	private void appendCondition(StringBuffer sb, SQLConditionalQuery query) {
		query.getCondition().ifPresent(condition -> {
			sb.append(" WHERE ");
			sb.append(condition.accept(getConditionVisitor()));
		});
	}
	
	private String escapeString(String s) {
		return s.replaceAll(Pattern.quote("'"), "''").replaceAll(Pattern.quote("\\"), "\\\\");
	}
	
}