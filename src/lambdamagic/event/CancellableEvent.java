package lambdamagic.event;

import java.util.EventListener;

import lambdamagic.data.Cancellable;


public abstract class CancellableEvent<T extends EventListener> extends Event<T> implements Cancellable {

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
