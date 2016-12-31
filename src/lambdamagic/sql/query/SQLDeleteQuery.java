package lambdamagic.sql.query;

import java.util.List;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLDeleteQuery implements SQLConditionalQuery {
	
	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;

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
	
	public SQLDeleteQuery(String tableName, List<SQLJoinClause> joinClauses, SQLCondition condition) {
		if (tableName == null)
			throw new NullArgumentException("tableName");
		
		if (joinClauses == null)
			throw new NullArgumentException("joinClauses");
		
		if (condition == null)
			throw new  NullArgumentException("condition");

		this.tableName = tableName;
		this.joinClauses = joinClauses;
		this.condition = condition;
	}
}
