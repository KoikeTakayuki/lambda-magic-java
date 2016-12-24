package lambdamagic.sql.query;

import lambdamagic.NullArgumentException;

public abstract class SQLQuery {
	
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}
	
	public SQLQuery(String tableName) {
		if (tableName == null)
			throw new NullArgumentException("tableName");

		this.tableName = tableName;
	}

}
