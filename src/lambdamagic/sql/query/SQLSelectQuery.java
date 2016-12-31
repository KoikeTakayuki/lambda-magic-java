package lambdamagic.sql.query;

import java.util.List;
import java.util.Map;

import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLSelectQuery implements SQLConditionalQuery {

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
	
	public SQLSelectQuery(String tableName, List<SQLJoinClause> joinClauses, SQLCondition condition,
			List<String> selectColumnNames, List<String> groupByColumnNames, Map<String, Boolean> orderByColumnMap,
			boolean orderAscending, int skipCount, int takeCount, boolean distinct, boolean count) {

		this.tableName = tableName;
		this.joinClauses = joinClauses;
		this.condition = condition;
		this.selectColumnNames = selectColumnNames;
		this.groupByColumnNames = groupByColumnNames;
		this.orderByColumnMap = orderByColumnMap;
		this.orderAscending = orderAscending;
		this.skipCount = skipCount;
		this.takeCount = takeCount;
		this.distinct = distinct;
		this.count = count;
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<SQLJoinClause> getJoinClauses() {
		return joinClauses;
	}

	@Override
	public SQLCondition getCondition() {
		return condition;
	}
	
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
}
