package lambdamagic.sql.query.builder;

import java.util.ArrayList;
import java.util.List;

import lambdamagic.sql.query.SQLDeleteQuery;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;

public class SQLDeleteQueryBuilder implements SQLQueryBuilder<SQLDeleteQuery> {

	private String tableName;
	private List<SQLJoinClause> joinClauses;
	private SQLCondition condition;

	private SQLDeleteQueryBuilder(String tableName) {
		this.tableName = tableName;
		this.joinClauses = new ArrayList<>();
	}
	
	public static SQLDeleteQueryBuilder deleteFrom(String tableName) {
		return new SQLDeleteQueryBuilder(tableName);
	}
	
	public SQLDeleteQueryBuilder where(SQLCondition condition) {
		this.condition = condition;
		return this;
	}
	
	@Override
	public SQLDeleteQuery build() {
		return new SQLDeleteQuery(tableName, joinClauses, condition);
	}

}
