package lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLUpdateQueryBuilder implements SQLQueryBuilder<SQLUpdateQuery> {

	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;
	private Map<String, Object> updateValues;

	private SQLUpdateQueryBuilder(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}

		this.tableName = tableName;
	}
	
	public static SQLUpdateQueryBuilder update(String tableName) {
		return new SQLUpdateQueryBuilder(tableName);
	}
	
	public SQLUpdateQueryBuilder set(Map<String, Object> updateValues) {
		if (updateValues == null) {
			throw new NullArgumentException("updateValues");
		}
			
		this.updateValues = updateValues;
		return this;
	}
	
	public SQLUpdateQueryBuilder where(SQLCondition condition) {
		if (condition == null) {
			throw new NullArgumentException("condition");
		}

		this.condition = condition;
		return this;
	}
	
	public SQLUpdateQueryBuilder joinOn(SQLJoinClause joinClause) {
		if (joinClauses == null) {
			joinClauses = new ArrayList<>();
		}
		
		joinClauses.add(joinClause);
		return this;
		
	}
	
	@Override
	public SQLUpdateQuery build() {
		if (updateValues == null || updateValues.size() < 1) {
			throw new IllegalStateException("updateValues should not be null or empty map");
		}
		
		return new SQLUpdateQuery(tableName, joinClauses, condition, updateValues);
	}

}
