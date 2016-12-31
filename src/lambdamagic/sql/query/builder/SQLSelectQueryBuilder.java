package lambdamagic.sql.query.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.collection.iterator.Iterables;
import lambdamagic.sql.query.SQLSelectQuery;
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
		this.tableName = tableName;
		this.joinClauses = new ArrayList<>();
		this.selectColumnNames = new ArrayList<>();
		this.groupByColumnNames = new ArrayList<>();
		this.orderByColumnMap = new HashMap<>();
		this.orderAscending = true;
		this.skipCount = -1;
		this.takeCount = -1;
		this.distinct = false;
		this.count = false;
	}
	
	@Override
	public SQLSelectQuery build() {
		return new SQLSelectQuery(tableName, joinClauses, condition, selectColumnNames, groupByColumnNames, orderByColumnMap, orderAscending, skipCount, takeCount, distinct, count);
	}
	
	public static SQLSelectQueryBuilder from(String tableName) {
		return new SQLSelectQueryBuilder(tableName);
	}
	
	public SQLSelectQueryBuilder select(Iterable<String> columnNames) {
		for (String columnName : columnNames)
			selectColumnNames.add(columnName);

		return this;
	}
	
	public SQLSelectQueryBuilder select(String... columnNames) {
		return select(Iterables.asIterable(columnNames));
	}
	
	public SQLSelectQueryBuilder groupBy(Iterable<String> columnNames) {
		for (String columnName : columnNames)
			groupByColumnNames.add(columnName);

		return this;
	}

	public SQLSelectQueryBuilder groupBy(String... columnNames) {
		return groupBy(Iterables.asIterable(columnNames));
	}

	public SQLSelectQueryBuilder orderBy(String columnName, boolean ascending) {
		if (columnName == null)
			throw new NullArgumentException("columnName");

		orderByColumnMap.put(columnName, ascending);

		return this;
	}
	
	public SQLSelectQueryBuilder skip(int count) {
		if (count < 0)
			throw new OutOfRangeArgumentException("count", "count < 0");
		
		this.skipCount = count;
		return this;
	}
	
	public SQLSelectQueryBuilder take(int count) {
		if (count < 0)
			throw new OutOfRangeArgumentException("count", "count < 0");
		
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
}
