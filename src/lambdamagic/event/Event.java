package lambdamagic.event;

import java.util.EventListener;

public abstract class Event<T extends EventListener> {

	public static final class Type<T> {
	}
	
	private Object source;
	
	public Object getSource() {
		return source;
	}
	
	public Event(Object source) {
		this.source = source;
	}
	
	public abstract Type<T> getType();
	public abstract void dispatch(T listener);
}
