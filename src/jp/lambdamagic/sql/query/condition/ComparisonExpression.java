package jp.lambdamagic.sql.query.condition;

import jp.lambdamagic.NullArgumentException;

public abstract class ComparisonExpression<T> implements SQLCondition {

	private String firstOperand;
	private T secondOperand;
	
	ComparisonExpression(String firstOperand, T secondOperand) {
		if (firstOperand == null) {
			throw new NullArgumentException("firstOperand");
		}
		
		if (secondOperand == null) {
			throw new NullArgumentException("secondOperand");
		}
		
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}
	
	public String getFirstOperand() {
		return firstOperand;
	}
	
	public T getSecondOperand() {
		return secondOperand;
	}

	public static class EqualToExpression extends ComparisonExpression<Object> {

		EqualToExpression(String firstOperand, Object secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}

	public static class NotEqualToExpression extends ComparisonExpression<Object> {

		NotEqualToExpression(String firstOperand, Object secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class GreaterThanExpression extends ComparisonExpression<Number> {
		
		GreaterThanExpression(String firstOperand, Number secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class LessThanExpression extends ComparisonExpression<Number> {
		
		LessThanExpression(String firstOperand, Number secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class GreaterOrEqualToExpression extends ComparisonExpression<Number> {
		
		GreaterOrEqualToExpression(String firstOperand, Number secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class LessOrEqualToExpression extends ComparisonExpression<Number> {
		
		LessOrEqualToExpression(String firstOperand, Number secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class ContainStringExpression extends ComparisonExpression<String> {
		
		ContainStringExpression(String firstOperand, String secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	
	public static class StartWithExpression extends ComparisonExpression<String> {
		
		StartWithExpression(String firstOperand, String secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class EndWithExpression extends ComparisonExpression<String> {
		
		EndWithExpression(String firstOperand, String secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}
	
	public static class InListExpression extends ComparisonExpression<Iterable<?>> {
		
		InListExpression(String firstOperand, Iterable<?> secondOperand) {
			super(firstOperand, secondOperand);
		}

		@Override
		public StringBuffer accept(SQLConditionVisitor visitor) {
			return visitor.visit(this);
		}
		
	}

}
