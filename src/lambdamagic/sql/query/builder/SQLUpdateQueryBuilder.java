package lambdamagic.sql.query.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.sql.query.SQLUpdateQuery;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLUpdateQueryBuilder implements SQLQueryBuilder<SQLUpdateQuery> {

	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;
	private Map<String, Object> updateValues;

	private SQLUpdateQueryBuilder(String tableName) {
		this.tableName = tableName;
		this.joinClauses = new ArrayList<>();
		this.updateValues = new HashMap<>();
	}
	
	public static SQLUpdateQueryBuilder update(String tableName) {
		return new SQLUpdateQueryBuilder(tableName);
	}
	
	public SQLUpdateQueryBuilder set(Map<String, Object> updateValues) {
		this.updateValues = updateValues;
		return this;
	}
	
	public SQLUpdateQueryBuilder where(SQLCondition condition) {
		this.condition = condition;
		return this;
	}
	
	@Override
	public SQLUpdateQuery build() {
		return new SQLUpdateQuery(tableName, joinClauses, condition, updateValues);
	}

}
