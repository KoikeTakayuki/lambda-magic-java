package jp.lambdamagic.sql.query.condition;

import jp.lambdamagic.NullArgumentException;

public class SQLJoinClause {
		
	public static enum JoinType {
		INNER,
		LEFT,
		RIGHT
	}
		
	private JoinType joinType;
	private String tableName;
	private SQLCondition condition;
		
	public SQLJoinClause(JoinType joinType, String tableName, SQLCondition condition) {
		if (joinType == null) {
			throw new NullArgumentException("joinType");
		}
			
		if (tableName == null) {
			throw new NullArgumentException("tableName");
		}

		if (condition == null) {
			throw new NullArgumentException("condition");
		}
	
		this.joinType = joinType;
		this.tableName = tableName;
		this.condition = condition;
	}
		
	public JoinType getJoinType() {
		return joinType;
	}
	
	public String getTableName() {
		return tableName;
	}

	public SQLCondition getCondition() {
		return condition;
	}
	
}
