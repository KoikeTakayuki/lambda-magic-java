package lambdamagic.sql.query;

import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLDeleteQuery implements SQLConditionalQuery {
	
	private String tableName;
	private Optional<List<SQLJoinClause>> joinClauses;
	private Optional<SQLCondition> condition;
	
	SQLDeleteQuery(String tableName, Optional<List<SQLJoinClause>> joinClauses, Optional<SQLCondition> condition) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (joinClauses == null) {
			throw new NullArgumentException("joinClauses");
		}
		
		if (condition == null) {
			throw new  NullArgumentException("condition");
		}

		this.tableName = tableName;
		this.joinClauses = joinClauses;
		this.condition = condition;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public Optional<List<SQLJoinClause>> getJoinClauses() {
		return joinClauses;
	}

	@Override
	public Optional<SQLCondition> getCondition() {
		return condition;
	}
}
