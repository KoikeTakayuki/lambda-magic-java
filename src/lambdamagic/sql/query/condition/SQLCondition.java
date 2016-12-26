package lambdamagic.sql.query.condition;
import lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNullExpression;
import lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNotNullExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.EqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.NotEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.LessThanExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.GreaterThanExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.LessOrEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.GreaterOrEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.ContainStringExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.StartWithExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.EndWithExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.InListExpression;
import lambdamagic.sql.query.condition.LogicExpression.AndExpression;
import lambdamagic.sql.query.condition.LogicExpression.OrExpression;
import lambdamagic.sql.query.condition.LogicExpression.NotExpression;

public interface SQLCondition {
	
	StringBuffer accept(SQLConditionVisitor visitor);
	
	/* Unary Operator Expression Factories */
	public static SQLCondition IS_NULL(String firstOperand) {
		return new IsNullExpression(firstOperand);
	}
	
	public static SQLCondition IS_NOT_NULL(String firstOperand) {
		return new IsNotNullExpression(firstOperand);
	}
	
	
	/* Comparison Expression Factories */	
	public static SQLCondition EQUAL_TO(String firstOperand, Object secondOperand) {
		return new EqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition NOT_EQUAL_TO(String firstOperand, Object secondOperand) {
		return new NotEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition LESS_THAN(String firstOperand, Number secondOperand) {
		return new LessThanExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition GREATER_THAN(String firstOperand, Number secondOperand) {
		return new GreaterThanExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition LESS_OR_EQUAL_TO(String firstOperand, Number secondOperand) {
		return new LessOrEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition GREATER_OR_EQUAL_TO(String firstOperand, Number secondOperand) {
		return new GreaterOrEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition CONTAIN(String firstOperand, String secondOperand) {
		return new ContainStringExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition START_WITH(String firstOperand, String secondOperand) {
		return new StartWithExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition END_WITH(String firstOperand, String secondOperand) {
		return new EndWithExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition IN_LIST(String firstOperand, Iterable<?> secondOperand) {
		return new InListExpression(firstOperand, secondOperand);
	}
	
	
	/* Logic Expression Factories */
	public static SQLCondition AND(SQLCondition... conditions) {
		return new AndExpression(conditions);
	}
	
	public static SQLCondition OR(SQLCondition... conditions) {
		return new OrExpression(conditions);
	}
	
	public static SQLCondition NOT(SQLCondition condition) {
		return new NotExpression(condition);
	}

}