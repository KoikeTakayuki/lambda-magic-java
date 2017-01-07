package jp.lambdamagic.sql.query;

import java.util.Map;

import jp.lambdamagic.NullArgumentException;

public class SQLUpdateQueryBuilder extends SQLConditionalQueryBuilder<SQLUpdateQuery, SQLUpdateQueryBuilder> {

	private Map<String, Object> updateValues;

	private SQLUpdateQueryBuilder(String tableName) {
		super(tableName);
	}
	
	public static SQLUpdateQueryBuilder update(String tableName) {
		return new SQLUpdateQueryBuilder(tableName);
	}

	public SQLUpdateQueryBuilder set(Map<String, Object> updateValues) {
		if (updateValues == null) {
			throw new NullArgumentException("updateValues");
		}
			
		this.updateValues = updateValues;
		return this;
	}

	@Override
	public SQLUpdateQuery build() {
		if (updateValues == null || updateValues.size() < 1) {
			throw new IllegalStateException("updateValues should not be null or empty map");
		}
		
		return new SQLUpdateQuery(tableName, joinClauses, condition, updateValues);
	}
	
}
