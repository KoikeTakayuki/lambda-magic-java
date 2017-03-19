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

public interface SQLConditionVisitor<T> {
    
    /* Unary Operator Expression */
    T visit(IsNullExpression expression);
    T visit(IsNotNullExpression expression);
    
    /* Comparison Expression */
    T visit(EqualToExpression expression);
    T visit(NotEqualToExpression expression);
    T visit(LessThanExpression expression);
    T visit(GreaterThanExpression expression);
    T visit(LessOrEqualToExpression expression);
    T visit(GreaterOrEqualToExpression expression);
    T visit(ContainStringExpression expression);
    T visit(StartWithExpression expression);
    T visit(EndWithExpression expression);
    T visit(InListExpression expression);

    /* Logic Expression */
    T visit(AndExpression expression);
    T visit(OrExpression expression);
    T visit(NotExpression expression);
}
