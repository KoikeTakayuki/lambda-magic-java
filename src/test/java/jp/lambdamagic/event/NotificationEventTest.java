package jp.lambdamagic.event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.event.NotificationEvent;

public class NotificationEventTest {

  @Test(expected=NullArgumentException.class)
  public void NotificationEvent_mustThrowNullArgumentExceptionWhenNullSourceIsGiven() {
    new NotificationEvent(null, "message");
  }
  
  @Test(expected=NullArgumentException.class)
  public void NotificationEvent_mustThrowNullArgumentExceptionWhenNullMessageIsGiven() {
    new NotificationEvent("source", null);
  }
  
  @Test
  public void getSource_returnGivenSource() {
    NotificationEvent event = new NotificationEvent("source", "message");
    assertThat(event.getSource(), is("source"));
  }

  @Test
  public void getMessage_returnGivenMessage() {
    NotificationEvent event = new NotificationEvent("source", "message");
    assertThat(event.getMessage(), is("message"));
  }
  
  @Test
  public void getType_returnNotificationEventType() {
    NotificationEvent event = new NotificationEvent("source", "message");
    assertThat(event.getType(), is(NotificationEvent.TYPE));
  }

}
