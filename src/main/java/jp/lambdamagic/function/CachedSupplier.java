package jp.lambdamagic.function;

import java.util.function.Supplier;

import jp.lambdamagic.NullArgumentException;

public class CachedSupplier<T> implements Supplier<T> {
  
  private Supplier<T> wrapped;
  private T value;

  public CachedSupplier(Supplier<T> wrapped) {
    if (wrapped == null) {
      throw new NullArgumentException("wrapped");
    }
    
    this.wrapped = wrapped;
  }

  @Override
  public T get() {
    if (value == null) {
      value = wrapped.get();
    }
    
    return value;
  }

}
