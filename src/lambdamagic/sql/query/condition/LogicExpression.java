package lambdamagic.sql.query.condition;

public class LogicExpression implements SQLCondition {

	static enum LogicOperator {
		NOT,
		AND,
		OR
 	}

	private LogicOperator operator;

	public LogicOperator getOperator() {
		return operator;
	}

	LogicExpression(LogicOperator operator, SQLCondition condition1, SQLCondition condition2) {
		this.operator = operator;
	}
	
	LogicExpression(LogicOperator operator, SQLCondition condition) {
		this.operator = operator;
	}
}
