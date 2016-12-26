package lambdamagic.sql.query.condition;

import lambdamagic.sql.query.condition.ComparisonExpression.ContainStringExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.EndWithExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.EqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.GreaterOrEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.GreaterThanExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.InListExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.LessOrEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.LessThanExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.NotEqualToExpression;
import lambdamagic.sql.query.condition.ComparisonExpression.StartWithExpression;
import lambdamagic.sql.query.condition.LogicExpression.AndExpression;
import lambdamagic.sql.query.condition.LogicExpression.NotExpression;
import lambdamagic.sql.query.condition.LogicExpression.OrExpression;
import lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNotNullExpression;
import lambdamagic.sql.query.condition.UnaryOperatorExpression.IsNullExpression;

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
