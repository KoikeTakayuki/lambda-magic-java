package lambdamagic.sql.query.builder;

import java.util.Map;

import lambdamagic.sql.query.SQLInsertQuery;

public class SQLInsertQueryBuilder implements SQLQueryBuilder<SQLInsertQuery> {

	private String tableName;
	private Map<String, ?> values;

	private SQLInsertQueryBuilder(String tableName) {
		this.tableName = tableName;
	}
	
	public static SQLInsertQueryBuilder insertInto(String tableName) {
		return new SQLInsertQueryBuilder(tableName);
	}
	
	public SQLInsertQueryBuilder values(Map<String, ?> values) {
		this.values = values;
		return this;
	}
	
	@Override
	public SQLInsertQuery build() {
		return new SQLInsertQuery(tableName, values);
	}

}
