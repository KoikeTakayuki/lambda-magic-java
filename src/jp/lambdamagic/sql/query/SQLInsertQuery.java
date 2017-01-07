package jp.lambdamagic.sql.query;

import java.util.Map;

import jp.lambdamagic.NullArgumentException;

public class SQLInsertQuery implements SQLQuery {
	
	private String tableName;
	private Map<String, Object> values;
	
	SQLInsertQuery(String tableName, Map<String, Object> values) {
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}
		
		if (values == null) {
			throw new NullArgumentException("values");
		}
		
		this.tableName = tableName;
		this.values = values;
	}

	@Override
	public String getTableName() {
		return tableName;
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
}
