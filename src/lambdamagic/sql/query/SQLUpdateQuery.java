package lambdamagic.sql.query;

import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLUpdateQuery implements SQLConditionalQuery {
	
	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;
	private Map<String, Object> updateValues;
	
	public SQLUpdateQuery(String tableName, List<SQLJoinClause> joinClauses, SQLCondition condition, Map<String, Object> updateValues) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (joinClauses == null)
			throw new NullArgumentException("joinClauses");
		
		if (condition == null)
			throw new NullArgumentException("condition");
		
		if (updateValues == null)
			throw new NullArgumentException("updateValues");
		
		this.tableName = tableName;
		this.joinClauses = joinClauses;
		this.condition = condition;
		this.updateValues = updateValues;
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
	
	public Map<String, Object> getUpdateValues() {
		return updateValues;
	}

}
