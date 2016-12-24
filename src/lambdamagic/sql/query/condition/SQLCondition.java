package lambdamagic.sql.query.condition;

public interface SQLCondition {
	
	public static SQLCondition AND(SQLCondition condition1, SQLCondition condition2) {
		return new LogicExpression(LogicExpression.LogicOperator.AND, condition1, condition2);
	}
	
	public static SQLCondition OR(SQLCondition condition1, SQLCondition condition2) {
		return new LogicExpression(LogicExpression.LogicOperator.OR, condition1, condition2);
	}
	
	public static SQLCondition NOT(SQLCondition condition) {
		return new LogicExpression(LogicExpression.LogicOperator.NOT, condition, condition);
	}
	
	public static SQLCondition EQUAL_TO(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.EQUAL_TO, firstOperand, secondOperand);
	}
	
	public static SQLCondition NOT_EQUAL_TO(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.NOT_EQUAL_TO, firstOperand, secondOperand);
	}
	
	public static SQLCondition LESS_THAN(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.LESS_THAN, firstOperand, secondOperand);
	}
	
	public static SQLCondition GREATER_THAN(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.GREATER_THAN, firstOperand, secondOperand);
	}
	
	public static SQLCondition LESS_OR_EQUAL_TO(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.LESS_OR_EQUAL_TO, firstOperand, secondOperand);
	}
	
	public static SQLCondition GREATER_OR_EQUAL_TO(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.GREATER_OR_EQUAL_TO, firstOperand, secondOperand);
	}
	
	public static SQLCondition CONTAIN_STRING(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.CONTAIN_STRING, firstOperand, secondOperand);
	}
	
	public static SQLCondition START_WITH_STRING(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.START_WITH_STRING, firstOperand, secondOperand);
	}
	
	public static SQLCondition END_WITH_STRING(String firstOperand, Object secondOperand) {
		return new ComparisonExpression(ComparisonExpression.Operator.END_WITH_STRING, firstOperand, secondOperand);
	}

}