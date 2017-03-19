package jp.lambdamagic.event;

import jp.lambdamagic.data.Cancellable;


public abstract class CancellableEvent extends Event implements Cancellable {

  private boolean cancelled;
  
  public CancellableEvent(Object source) {
    super(source);
  }
  
  @Override
  public boolean isCancelled() {
    return cancelled;
  }
  
  @Override
  public void cancel() {
    this.cancelled = true;
  }
  
}
