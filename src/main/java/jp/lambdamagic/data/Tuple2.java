package jp.lambdamagic.data;

import java.io.Serializable;

import jp.lambdamagic.NullArgumentException;

public final class Tuple2<T, S> implements Serializable {

  private static final long serialVersionUID = 692990897984802517L;

  private T firstValue;
  private S secondValue;
  
  public Tuple2(T firstValue, S secondValue) {
    if (firstValue == null)
      throw new NullArgumentException("firstValue");
    
    if (secondValue == null)
      throw new NullArgumentException("secondValue");
    
    this.firstValue = firstValue;
    this.secondValue = secondValue;
  }

  public T getFirstValue() {
    return firstValue;
  }

  public S getSecondValue() {
    return secondValue;
  }

  @Override
  public String toString() {
    return "Tuple2(" + firstValue + ", "+ secondValue + ")";
  }
  
}