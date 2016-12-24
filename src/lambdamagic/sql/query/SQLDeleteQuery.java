package lambdamagic.sql.query;

import lambdamagic.sql.query.condition.SQLCondition;

public class SQLDeleteQuery extends SQLConditionalQuery {

	private SQLDeleteQuery(String tableName) {
		super(tableName);
	}
	
	public static SQLDeleteQuery from(String tableName) {
		return new SQLDeleteQuery(tableName);
	}
	
	@Override
	public SQLDeleteQuery joinOn(String tableName, String columnName1, String columnName2) {
		return (SQLDeleteQuery)super.joinOn(tableName, columnName1, columnName2);
	}
	
	@Override
	public SQLDeleteQuery where(SQLCondition condition) {
		return (SQLDeleteQuery)super.where(condition);
	}

}
