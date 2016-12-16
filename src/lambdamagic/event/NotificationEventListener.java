package lambdamagic.event;

import java.util.EventListener;

@FunctionalInterface
public interface NotificationEventListener extends EventListener {
	void onNotificationReceived(NotificationEvent e);
}
