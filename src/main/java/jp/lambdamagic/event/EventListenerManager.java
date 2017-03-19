package jp.lambdamagic.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.lambdamagic.NullArgumentException;

public class EventListenerManager {

    private Map<Event.Type<?>, List<EventListener<?>>> listenerMap;
    
    public EventListenerManager() {
        this.listenerMap = new HashMap<Event.Type<?>, List<EventListener<?>>>();
    }
    
    public <E extends Event> EventListenerRegistration addEventListener(Event.Type<E> type, EventListener<E> listener) {
        if (type == null) {
            throw new NullArgumentException("type");
        }
        
        if (listener == null) {
            throw new NullArgumentException("listener");
        }
        
        List<EventListener<?>> listeners = listenerMap.get(type);
        
        if (listeners == null) {
            listeners = new ArrayList<EventListener<?>>();
            listenerMap.put(type, listeners);
        }
        
        listeners.add(listener);
        
        final List<EventListener<?>> finalListeners = listeners;
        
        return () -> finalListeners.remove(listener);
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Event> void fireEvent(E event) {
        if (event == null) {
            throw new NullArgumentException("event");
        }
        
        List<EventListener<E>> listeners = (List<EventListener<E>>)(List<?>)listenerMap.get(event.getType());
        
        if (listeners == null) {
            return;
        }
        
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onEvent(event);
        }
    }
    
}
