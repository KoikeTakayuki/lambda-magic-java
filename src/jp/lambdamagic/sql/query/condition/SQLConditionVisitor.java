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

public interface SQLConditionVisitor {
	
	/* Unary Operator Expression */
	StringBuffer visit(IsNullExpression expression);
	StringBuffer visit(IsNotNullExpression expression);
	
	/* Comparison Expression */
	StringBuffer visit(EqualToExpression expression);
	StringBuffer visit(NotEqualToExpression expression);
	StringBuffer visit(LessThanExpression expression);
	StringBuffer visit(GreaterThanExpression expression);
	StringBuffer visit(LessOrEqualToExpression expression);
	StringBuffer visit(GreaterOrEqualToExpression expression);
	StringBuffer visit(ContainStringExpression expression);
	StringBuffer visit(StartWithExpression expression);
	StringBuffer visit(EndWithExpression expression);
	StringBuffer visit(InListExpression expression);

	/* Logic Expression */
	StringBuffer visit(AndExpression expression);
	StringBuffer visit(OrExpression expression);
	StringBuffer visit(NotExpression expression);
}
