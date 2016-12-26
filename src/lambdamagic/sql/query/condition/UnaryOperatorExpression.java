package lambdamagic.sql.query.condition;

public abstract class UnaryOperatorExpression implements SQLCondition {
	
	private String operand;
	
	public String getOperand() {
		return operand;
	}
	
	public UnaryOperatorExpression(String operand) {
		this.operand = operand;
	}

	public static class IsNullExpression extends UnaryOperatorExpression {

		IsNullExpression(String operand) {
			super(operand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class IsNotNullExpression extends UnaryOperatorExpression {
		
		IsNotNullExpression(String operand) {
			super(operand);
		}
		
		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
	}
}
