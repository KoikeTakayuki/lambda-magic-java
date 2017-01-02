package lambdamagic.event;


public class NotificationEvent extends Event<NotificationEventListener> {

	public static final Type<NotificationEventListener> TYPE = new Type<NotificationEventListener>();
	
	private String message;
	
	public NotificationEvent(Object source, String message) {
		super(source);
		
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public Type<NotificationEventListener> getType() {
		return TYPE;
	}

	@Override
	public void dispatch(NotificationEventListener listener) {
		listener.onNotificationReceived(this);
	}
}
