package jp.lambdamagic.event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.event.EventListenerManager;
import jp.lambdamagic.event.NotificationEvent;

public class EventListenerManagerTest {

    @Test(expected=NullArgumentException.class)
    public void addEventListener_mustThrowNullArgumentExceptionWhenNullTypeIsGiven() {
        EventListenerManager eventListenerManager = new EventListenerManager();
        eventListenerManager.addEventListener(null, e -> {});
    }
    
    @Test(expected=NullArgumentException.class)
    public void addEventListener_mustThrowNullArgumentExceptionWhenNullListenerIsGiven() {
        EventListenerManager eventListenerManager = new EventListenerManager();
        eventListenerManager.addEventListener(NotificationEvent.TYPE, null);
    }
    
    @Test
    public void fireEvent_executeRegisteredEventListener() {
        EventListenerManager eventListenerManager = new EventListenerManager();
        
        List<Integer> list = new ArrayList<>();
        
        eventListenerManager.addEventListener(NotificationEvent.TYPE, e -> {
            list.add(100);
            list.add(200);
        });
        
        assertThat(list, is(Arrays.asList()));
        eventListenerManager.fireEvent(new NotificationEvent(100, "test"));
        assertThat(list, is(Arrays.asList(100, 200)));
    }

}
