package lambdamagic.sql.query;

import java.util.ArrayList;
import java.util.List;

import lambdamagic.NullArgumentException;
import lambdamagic.sql.query.condition.SQLCondition;
import lambdamagic.sql.query.condition.SQLJoinClause;


public abstract class SQLConditionalQuery extends SQLQuery {
	
	private List<SQLJoinClause> joinClauses;
	private SQLCondition whereCondition;
	
	public List<SQLJoinClause> getJoinClauses() {
		return joinClauses;
	}

	public SQLCondition getCondition() {
		return whereCondition;
	}

	public SQLConditionalQuery(String tableName) {
		super(tableName);

		this.joinClauses = new ArrayList<>();
	}
	
	public SQLQuery joinOn(String tableName, String columnName1, String columnName2) {
		if (tableName == null)
			throw new NullArgumentException("tableNmae");
		
		if (columnName1 == null)
			throw new NullArgumentException("columnName1");
		
		if (columnName2 == null)
			throw new NullArgumentException("columnName2");

		joinClauses.add(new SQLJoinClause(SQLJoinClause.JoinType.INNER, tableName, SQLCondition.EQUAL_TO(columnName1, columnName2)));

		return this;
	}

	public SQLQuery where(SQLCondition condition) {
		if (condition == null)
			throw new NullArgumentException("condition");

		addCondition(condition);

		return this;
	}
	
	private void addCondition(SQLCondition condition) {
		if (whereCondition == null)
			whereCondition = condition;
		else
			whereCondition = SQLCondition.AND(whereCondition, condition);
	}

}
