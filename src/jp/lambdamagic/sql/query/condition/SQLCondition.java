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
	public static SQLCondition isNull(String firstOperand) {
		return new IsNullExpression(firstOperand);
	}
	
	public static SQLCondition isNotNull(String firstOperand) {
		return new IsNotNullExpression(firstOperand);
	}
	
	
	/* Comparison Expression Factories */	
	public static EqualToExpression equalTo(String firstOperand, Object secondOperand) {
		return new EqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition notEqualTo(String firstOperand, Object secondOperand) {
		return new NotEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition lessThan(String firstOperand, Number secondOperand) {
		return new LessThanExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition greaterThan(String firstOperand, Number secondOperand) {
		return new GreaterThanExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition lessOrEqualTo(String firstOperand, Number secondOperand) {
		return new LessOrEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition greaterOrEqualTo(String firstOperand, Number secondOperand) {
		return new GreaterOrEqualToExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition contain(String firstOperand, String secondOperand) {
		return new ContainStringExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition startWith(String firstOperand, String secondOperand) {
		return new StartWithExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition endWith(String firstOperand, String secondOperand) {
		return new EndWithExpression(firstOperand, secondOperand);
	}
	
	public static SQLCondition inList(String firstOperand, Iterable<?> secondOperand) {
		return new InListExpression(firstOperand, secondOperand);
	}
	
	
	/* Logic Expression Factories */
	public static SQLCondition and(SQLCondition... conditions) {
		return new AndExpression(conditions);
	}
	
	public static SQLCondition or(SQLCondition... conditions) {
		return new OrExpression(conditions);
	}
	
	public static SQLCondition not(SQLCondition condition) {
		return new NotExpression(condition);
	}

}