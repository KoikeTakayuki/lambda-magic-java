package jp.lambdamagic.event;

public abstract class FallibleEvent<E extends Exception> extends Event {

  private E exception;
  
  public FallibleEvent(Object source) {
    super(source);
  }
  
  public boolean hasFailed() {
    return (exception != null);
  }
  
  public E getException() {
    return exception;
  }
  
  public void failWith(E exception) {
    this.exception = exception;
  }
  
}
