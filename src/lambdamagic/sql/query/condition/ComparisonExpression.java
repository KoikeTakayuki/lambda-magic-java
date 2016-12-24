package lambdamagic.sql.query.condition;

import lambdamagic.NullArgumentException;

public class ComparisonExpression implements SQLCondition {

	static enum Operator {
		IS_NULL,
		IS_NOT_NULL,
		EQUAL_TO,
		NOT_EQUAL_TO,
		LESS_THAN,
		GREATER_THAN,
		LESS_OR_EQUAL_TO,
		GREATER_OR_EQUAL_TO,
		CONTAIN_STRING,
		START_WITH_STRING,
		END_WITH_STRING,
		IN_LIST,
	}

	private Operator operator;
	private String firstOperand;
	private Object secondOperand;
	
	public Operator getOperator() {
		return operator;
	}
	
	public String getFirstOperand() {
		return firstOperand;
	}
	
	public Object getSecondOperand() {
		return secondOperand;
	}
	
	ComparisonExpression(Operator operator, String firstOperand, Object secondOperand) {
		if (operator == null)
			throw new NullArgumentException("operator");
		
		if (firstOperand == null)
			throw new NullArgumentException("firstOperand");
		
		if (secondOperand == null)
			throw new NullArgumentException("secondOperand");
		
		this.operator = operator;
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}
}
