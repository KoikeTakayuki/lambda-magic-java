package lambdamagic.sql.query;

import java.util.Map;

import lambdamagic.NullArgumentException;

public class SQLInsertQueryBuilder implements SQLQueryBuilder<SQLInsertQuery> {

	private String tableName;
	private Map<String, ?> insertValues;

	private SQLInsertQueryBuilder(String tableName) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}

		this.tableName = tableName;
	}
	
	public static SQLInsertQueryBuilder insertInto(String tableName) {
		return new SQLInsertQueryBuilder(tableName);
	}
	
	public SQLInsertQueryBuilder values(Map<String, ?> insertValues) {
		if (insertValues == null) {
			throw new NullArgumentException("insertValues");
		}

		this.insertValues = insertValues;
		return this;
	}
	
	@Override
	public SQLInsertQuery build() {
		if (insertValues == null || insertValues.size() < 1) {
			throw new IllegalStateException("insertValues should not be null or empty map");
		}
		
		return new SQLInsertQuery(tableName, insertValues);
	}

}
