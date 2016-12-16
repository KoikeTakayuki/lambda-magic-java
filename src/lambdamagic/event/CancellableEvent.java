package lambdamagic.event;

import java.util.EventListener;

public abstract class CancellableEvent<T extends EventListener> extends Event<T> {

	private boolean cancelled;
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public CancellableEvent(Object source) {
		super(source);
	}
	
	public void cancel() {
		this.cancelled = true;
	}
}
