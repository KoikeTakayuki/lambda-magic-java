package lambdamagic.event;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lambdamagic.NullArgumentException;

public class EventListenerManager {

	private Map<Event.Type<?>, List<EventListener>> listenerMap;
	
	public EventListenerManager() {
		this.listenerMap = new HashMap<Event.Type<?>, List<EventListener>>();
	}
	
	public <T extends EventListener> EventListenerRegistration addEventListener(Event.Type<T> type, final T listener) {
		if (type == null)
			throw new NullArgumentException("type");
		
		if (listener == null)
			throw new NullArgumentException("listener");
		
		List<EventListener> listeners = listenerMap.get(type);
		if (listeners == null) {
			listeners = new ArrayList<EventListener>();
			listenerMap.put(type, listeners);
		}
		listeners.add(listener);
		
		final List<EventListener> finalListeners = listeners;
		return new EventListenerRegistration() {
			
			@Override
			public void remove() {
				finalListeners.remove(listener);
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EventListener> void fireEvent(Event<T> event) {
		if (event == null)
			throw new NullArgumentException("event");
		
		List<EventListener> listeners = listenerMap.get(event.getType());
		if (listeners == null)
			return;
		
		for (int i = 0; i < listeners.size(); ++i)
			event.dispatch((T)listeners.get(i));
	}
}
