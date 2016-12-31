package lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.collection.iterator.Iterables;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLSelectQueryBuilder implements SQLQueryBuilder<SQLSelectQuery> {
	
	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;
	private List<String> selectColumnNames;
	private List<String> groupByColumnNames;
	private Map<String, Boolean> orderByColumnMap;
	private boolean orderAscending;
	private int skipCount;
	private int takeCount;
	private boolean distinct;
	private boolean count;
	
	private SQLSelectQueryBuilder(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		this.tableName = tableName;
		this.orderAscending = true;
		this.skipCount = -1;
		this.takeCount = -1;
		this.distinct = false;
		this.count = false;
	}
	
	@Override
	public SQLSelectQuery build() {
		return new SQLSelectQuery(tableName, joinClauses, condition,
				selectColumnNames, groupByColumnNames, orderByColumnMap,
				orderAscending, skipCount, takeCount, distinct, count);
	}
	
	public static SQLSelectQueryBuilder from(String tableName) {
		return new SQLSelectQueryBuilder(tableName);
	}
	
	public SQLSelectQueryBuilder select(Iterable<String> columnNames) {
		if (columnNames == null) {
			throw new NullArgumentException("columnNames");
		}
		
		if (selectColumnNames == null) {
			selectColumnNames = new ArrayList<>();
		}
		
		for (String columnName : columnNames) {
			selectColumnNames.add(columnName);
		}

		return this;
	}
	
	public SQLSelectQueryBuilder select(String... columnNames) {
		return select(Iterables.asIterable(columnNames));
	}
	
	public SQLSelectQueryBuilder groupBy(Iterable<String> columnNames) {
		if (columnNames == null) {
			throw new NullArgumentException("columnNames");
		}
		
		if (groupByColumnNames == null) {
			groupByColumnNames = new ArrayList<>();
		}

		for (String columnName : columnNames) {
			groupByColumnNames.add(columnName);
		}

		return this;
	}

	public SQLSelectQueryBuilder groupBy(String... columnNames) {
		return groupBy(Iterables.asIterable(columnNames));
	}

	public SQLSelectQueryBuilder orderBy(String columnName, boolean ascending) {
		if (columnName == null) {
			throw new NullArgumentException("columnName");
		}
		
		if (orderByColumnMap == null) {
			orderByColumnMap = new HashMap<>();
		}

		orderByColumnMap.put(columnName, ascending);
		return this;
	}
	
	public SQLSelectQueryBuilder skip(int count) {
		if (count < 1) {
			throw new OutOfRangeArgumentException("count", "count < 1");
		}
		
		this.skipCount = count;
		return this;
	}
	
	public SQLSelectQueryBuilder take(int count) {
		if (count < 1) {
			throw new OutOfRangeArgumentException("count", "count < 1");
		}
		
		this.takeCount = count;
		return this;
	}
	
	public SQLSelectQueryBuilder distinct() {
		distinct = true;
		return this;
	}

	public SQLSelectQueryBuilder count() {
		count = true;
		return this;
	}
	
	public SQLSelectQueryBuilder where(SQLCondition condition) {
		if (condition == null) {
			throw new NullArgumentException("condition");
		}

		this.condition = condition;
		return this;
	}
	
	public SQLSelectQueryBuilder joinOn(SQLJoinClause joinClause) {
		if (joinClauses == null) {
			this.joinClauses = new ArrayList<>();
		}

		this.joinClauses.add(joinClause);
		return this;
	}
}
