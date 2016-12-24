package lambdamagic.sql.query;

import java.util.HashMap;
import java.util.Map;

import lambdamagic.sql.query.condition.SQLCondition;

public class SQLUpdateQuery extends SQLConditionalQuery {
	
	private Map<String, Object> updateValues;
	
	public Map<String, Object> getUpdateValues() {
		return updateValues;
	}

	private SQLUpdateQuery(String tableName) {
		super(tableName);
		
		this.updateValues = new HashMap<>();
	}

	public static SQLUpdateQuery from(String tableName) {
		return new SQLUpdateQuery(tableName);
	}
	
	@Override
	public SQLUpdateQuery joinOn(String tableName, String columnName1, String columnName2) {
		return (SQLUpdateQuery)super.joinOn(tableName, columnName1, columnName2);
	}
	
	@Override
	public SQLUpdateQuery where(SQLCondition condition) {
		return (SQLUpdateQuery)super.where(condition);
	}

}
