package jp.lambdamagic.sql.mysql;

import java.util.regex.Pattern;

import jp.lambdamagic.sql.query.condition.ComparisonExpression;
import jp.lambdamagic.sql.query.condition.SQLCondition;
import jp.lambdamagic.sql.query.condition.SQLConditionVisitor;
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

public class MySQLConditionVisitor implements SQLConditionVisitor {
	
	private StringBuffer buffer = new StringBuffer();
	
	protected StringBuffer getBuffer() {
		return buffer;
	}

	/* Unary Operator Expression */
	@Override
	public StringBuffer visit(IsNullExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getOperand());
		sb.append(" IS NULL");
		return sb;
	}

	@Override
	public StringBuffer visit(IsNotNullExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getOperand());
		sb.append(" IS NOT NULL");
		return sb;
	}

	
	/* Comparison Expression */
	@Override
	public StringBuffer visit(EqualToExpression expression) {
		return withComparisonOperator(expression, "=");
	}

	@Override
	public StringBuffer visit(NotEqualToExpression expression) {
		return withComparisonOperator(expression, "<>");
	}
	
	@Override
	public StringBuffer visit(LessThanExpression expression) {
		return withComparisonOperator(expression, "<");
	}

	@Override
	public StringBuffer visit(GreaterThanExpression expression) {
		return withComparisonOperator(expression, ">");
	}
	
	@Override
	public StringBuffer visit(LessOrEqualToExpression expression) {
		return withComparisonOperator(expression, "<=");
	}

	@Override
	public StringBuffer visit(GreaterOrEqualToExpression expression) {
		return withComparisonOperator(expression, ">=");
	}

	@Override
	public StringBuffer visit(ContainStringExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getFirstOperand());
		sb.append(" LIKE ");
		appendString(sb, "%" + expression.getSecondOperand() + "%");
		return sb;
	}

	@Override
	public StringBuffer visit(StartWithExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getFirstOperand());
		sb.append(" LIKE ");
		appendString(sb, expression.getSecondOperand() + "%");
		return sb;
	}

	@Override
	public StringBuffer visit(EndWithExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getFirstOperand());
		sb.append(" LIKE ");
		appendString(sb, "%" + expression.getSecondOperand());
		return sb;
	}

	@Override
	public StringBuffer visit(InListExpression expression) {
		StringBuffer sb = getBuffer();
		return sb;
	}

	/* Logic Expression */
	@Override
	public StringBuffer visit(AndExpression expression) {
		SQLCondition[] operands = expression.getOperands();
		return withLogicOperator(operands, "AND");
	}

	@Override
	public StringBuffer visit(OrExpression expression) {
		SQLCondition[] operands = expression.getOperands();
		return withLogicOperator(operands, "OR");
	}

	@Override
	public StringBuffer visit(NotExpression expression) {
		StringBuffer sb = getBuffer();
		sb.append("NOT (");
		expression.getOperand().accept(this);
		sb.append(')');
		return sb;
	}
	
	private StringBuffer withLogicOperator(SQLCondition[] operands, String logicOperator) {
		StringBuffer sb = getBuffer();
		
		sb.append('(');
		
		for (int i = 0; i < operands.length; ++i) {
			SQLCondition operand = operands[i];
			operand.accept(this);
			
			if (i < operands.length - 1) {
				sb.append(' ');
				sb.append(logicOperator);
				sb.append(' ');
			}
		}
		
		sb.append(')');
		return sb;
	}
	
	private StringBuffer withComparisonOperator(ComparisonExpression<?> expression, String operator) {
		StringBuffer sb = getBuffer();
		sb.append(expression.getFirstOperand());
		sb.append(" ");
		sb.append(operator);
		sb.append(" ");
		sb.append(expression.getSecondOperand());
		return sb;
	}
	
	private void appendString(StringBuffer sb, String value) {
		sb.append('\'');
		sb.append(escapeString(value));
		sb.append('\'');
	}
	
	private String escapeString(String s) {
		return s.replaceAll(Pattern.quote("'"), "''").replaceAll(Pattern.quote("\\"), "\\\\");
	}
	
}
