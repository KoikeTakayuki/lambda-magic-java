package lambdamagic.event;

import java.util.EventListener;

public abstract class FallibleEvent<T extends EventListener, E extends Exception> extends Event<T> {

	private E exception;
	
	public boolean hasFailed() {
		return (exception != null);
	}
	
	public E getException() {
		return exception;
	}
	
	public FallibleEvent(Object source) {
		super(source);
	}
	
	public void failWith(E exception) {
		this.exception = exception;
	}
}
