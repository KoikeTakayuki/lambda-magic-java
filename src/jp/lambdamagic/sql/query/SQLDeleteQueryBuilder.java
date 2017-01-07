package jp.lambdamagic.sql.query;

public class SQLDeleteQueryBuilder extends SQLConditionalQueryBuilder<SQLDeleteQuery, SQLDeleteQueryBuilder> {

	private SQLDeleteQueryBuilder(String tableName) {
		super(tableName);
	}
	
	public static SQLDeleteQueryBuilder deleteFrom(String tableName) {
		return new SQLDeleteQueryBuilder(tableName);
	}

	@Override
	public SQLDeleteQuery build() {
		return new SQLDeleteQuery(tableName, joinClauses, condition);
	}

}
