package jp.lambdamagic.sql.query.condition;

import jp.lambdamagic.NullArgumentException;

public abstract class LogicExpression implements SQLCondition {
	
	public static class AndExpression extends LogicExpression {
		
		private SQLCondition[] operands;
		
		public SQLCondition[] getOperands() {
			return operands;
		}
		
		AndExpression(SQLCondition... operands) {
			if (operands == null) {
				throw new NullArgumentException("operands");
			}
			
			this.operands = operands;
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class OrExpression extends LogicExpression {
		
		private SQLCondition[] operands;
		
		public SQLCondition[] getOperands() {
			return operands;
		}
		
		OrExpression(SQLCondition... operands) {
			if (operands == null) {
				throw new NullArgumentException("operands");
			}
			
			this.operands = operands;
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class NotExpression extends LogicExpression {
		
		private SQLCondition operand;
		
		public SQLCondition getOperand() {
			return operand;
		}
		
		NotExpression(SQLCondition operand) {
			if (operand == null) {
				throw new NullArgumentException("operand");
			}
			
			this.operand = operand;
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor) {
			return visitor.visit(this);
		}
		
	}
	
}
