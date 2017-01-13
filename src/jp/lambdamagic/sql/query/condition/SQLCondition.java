package jp.lambdamagic.sql.query.condition;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.ContainStringExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.EndWithExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.EqualToExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.GreaterOrEqualToExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.GreaterThanExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.InListExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.LessOrEqualToExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.LessThanExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.NotEqualToExpression;
import jp.lambdamagic.sql.query.condition.ComparisonExpression.StartWithExpression;
import jp.lambdamagic.sql.query.condition.LogicExpression.AndExpression;
import jp.lambdamagic.sql.query.condition.LogicExpression.NotExpression;
import jp.lambdamagic.sql.query.condition.LogicExpression.OrExpression;
import jp.lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNotNullExpression;
import jp.lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNullExpression;

public interface SQLCondition {
	
	StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor);
	
	/* Unary Operator Expression Factories */
	public static SQLCondition IS_NULL(String firstOperand) {
		return new IsNullExpression(firstOperand);
	}
	
	public static SQLCondition IS_NOT_NULL(String firstOperand) {
		return new IsNotNullExpression(firstOperand);
	}
	
	
	/* Comparison Expression Factories */	
	public static EqualToExpression EQUAL_TO(String firstOperand, Object secondOperand) {
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