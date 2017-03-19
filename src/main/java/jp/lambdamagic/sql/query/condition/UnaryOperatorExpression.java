package jp.lambdamagic.sql.query.condition;

public abstract class UnaryOperatorExpression implements SQLCondition {

  public static class IsNullExpression extends UnaryOperatorExpression {

    IsNullExpression(String operand) {
      super(operand);
    }

    @Override
    public StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor) {
      return visitor.visit(this);
    }
    
  }
  
  public static class IsNotNullExpression extends UnaryOperatorExpression {
    
    IsNotNullExpression(String operand) {
      super(operand);
    }
    
    @Override
    public StringBuffer accept(SQLConditionVisitor<StringBuffer> visitor) {
      return visitor.visit(this);
    }
    
  }
  
  private String operand;
  
  public UnaryOperatorExpression(String operand) {
    this.operand = operand;
  }

  public String getOperand() {
    return operand;
  }
  
}
