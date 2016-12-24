package lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.collection.iterator.Iterables;
import lambdamagic.sql.query.condition.SQLCondition;

public class SQLSelectQuery extends SQLConditionalQuery {
	
	private List<String> selectColumnNames;
	private List<String> groupByColumnNames;
	private Map<String, Boolean> orderByColumnMap;
	private boolean orderAscending;
	private int skipCount;
	private int takeCount;
	private boolean distinct;
	private boolean count;
	
	public List<String> getSelectColumnNames() {
		return selectColumnNames;
	}
	
	public List<String> getGroupByColumnNames() {
		return groupByColumnNames;
	}
	
	public Map<String, Boolean> getOrderColumnMap() {
		return orderByColumnMap;
	}
	
	public boolean isOrderAscending() {
		return orderAscending;
	}
	
	public int getSkipCount() {
		return skipCount;
	}
	
	public int getTakeCount() {
		return takeCount;
	}
	
	public boolean isDistinct() {
		return distinct;
	}
	
	public boolean isCount() {
		return count;
	}

	private SQLSelectQuery(String tableName) {
		super(tableName);

		this.selectColumnNames = new ArrayList<>();
		this.groupByColumnNames = new ArrayList<>();
		this.orderByColumnMap = new HashMap<>();
	}
	
	public static SQLSelectQuery from(String tableName) {
		return new SQLSelectQuery(tableName);
	}
	
	public SQLSelectQuery select(Iterable<String> columnNames) {
		for (String columnName : columnNames)
			selectColumnNames.add(columnName);

		return this;
	}
	
	public SQLSelectQuery select(String... columnNames) {
		return select(Iterables.asIterable(columnNames));
	}
	
	public SQLSelectQuery groupBy(Iterable<String> columnNames) {
		for (String columnName : columnNames)
			groupByColumnNames.add(columnName);

		return this;
	}

	public SQLSelectQuery groupBy(String... columnNames) {
		return groupBy(Iterables.asIterable(columnNames));
	}

	public SQLSelectQuery orderBy(String columnName, boolean ascending) {
		if (columnName == null)
			throw new NullArgumentException("columnName");

		orderByColumnMap.put(columnName, ascending);

		return this;
	}
	
	public SQLSelectQuery skip(int count) {
		if (count < 0)
			throw new OutOfRangeArgumentException("count", "count < 0");
		
		this.skipCount = count;
		return this;
	}
	
	public SQLSelectQuery take(int count) {
		if (count < 0)
			throw new OutOfRangeArgumentException("count", "count < 0");
		
		this.takeCount = count;
		return this;
	}
	
	public SQLSelectQuery distinct() {
		distinct = true;
		return this;
	}

	public SQLSelectQuery count() {
		count = true;
		return this;
	}
	
	@Override
	public SQLSelectQuery joinOn(String tableName, String columnName1, String columnName2) {
		return (SQLSelectQuery)super.joinOn(tableName, columnName1, columnName2);
	}
	
	@Override
	public SQLSelectQuery where(SQLCondition condition) {
		return (SQLSelectQuery)super.where(condition);
	}

}
