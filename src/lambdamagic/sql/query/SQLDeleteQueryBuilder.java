package lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLDeleteQueryBuilder implements SQLQueryBuilder<SQLDeleteQuery> {

	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;

	private SQLDeleteQueryBuilder(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}

		this.tableName = tableName;
	}
	
	public static SQLDeleteQueryBuilder deleteFrom(String tableName) {
		return new SQLDeleteQueryBuilder(tableName);
	}
	
	public SQLDeleteQueryBuilder where(SQLCondition condition) {
		if (condition == null) {
			throw new NullArgumentException("condition");
		}

		this.condition = condition;
		return this;
	}
	
	public SQLDeleteQueryBuilder joinOn(SQLJoinClause joinClause) {
		if (joinClauses == null) {
			this.joinClauses = new ArrayList<>();
		}

		this.joinClauses.add(joinClause);
		return this;
	}
	
	@Override
	public SQLDeleteQuery build() {
		Optional<List<SQLJoinClause>> deleteQueryJoinClauses = (joinClauses == null) ?
				Optional.empty() : Optional.of(joinClauses);
		
		Optional<SQLCondition> deleteQueryCondition = (condition == null) ?
				Optional.empty() : Optional.of(condition); 

		return new SQLDeleteQuery(tableName, deleteQueryJoinClauses, deleteQueryCondition);
	}

}
