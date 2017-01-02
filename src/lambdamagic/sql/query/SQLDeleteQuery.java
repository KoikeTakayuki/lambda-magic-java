package lambdamagic.sql.query;

import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLDeleteQuery implements SQLConditionalQuery {
	
	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;
	
	SQLDeleteQuery(String tableName, List<SQLJoinClause> joinClauses, SQLCondition condition) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
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
		if (joinClauses == null) {
			return Optional.empty();
		}
		
		return Optional.of(joinClauses);
	}

	@Override
	public Optional<SQLCondition> getCondition() {
		if (condition == null) {
			return Optional.empty();
		}
		
		return Optional.of(condition);
	}
	
}
