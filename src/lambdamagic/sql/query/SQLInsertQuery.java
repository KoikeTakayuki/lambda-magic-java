package lambdamagic.sql.query;

import java.util.Map;

import lambdamagic.NullArgumentException;

public class SQLInsertQuery extends SQLQuery {
	
	private Map<String, ?> values;
	
	public Map<String, ?> getValues() {
		return values;
	}

	public SQLInsertQuery(String tableName, Map<String, ?> values) {
		super(tableName);
		
		if (values == null)
			throw new NullArgumentException("values");
		
		this.values = values;
	}

}
