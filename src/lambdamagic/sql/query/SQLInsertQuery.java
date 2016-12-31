package lambdamagic.sql.query;

import java.util.Map;

import lambdamagic.NullArgumentException;

public class SQLInsertQuery implements SQLQuery {
	
	private String tableName;
	private Map<String, ?> values;
	
	SQLInsertQuery(String tableName, Map<String, ?> values) {
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
	
	public Map<String, ?> getValues() {
		return values;
	}
}
