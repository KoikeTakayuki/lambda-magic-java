package jp.lambdamagic.event;

import jp.lambdamagic.NullArgumentException;

public abstract class Event {

  public static final class Type<E extends Event> {}
  
  private Object source;
  
  public Event(Object source) {
    if (source == null) {
      throw new NullArgumentException("source");
    }
    
    this.source = source;
  }
  
  public abstract Type<? extends Event> getType();
  
  public Object getSource() {
    return source;
  }
  
}
