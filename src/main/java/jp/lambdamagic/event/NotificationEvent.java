package jp.lambdamagic.event;

import jp.lambdamagic.NullArgumentException;

public class NotificationEvent extends Event {
    
    public static final Type<NotificationEvent> TYPE = new Type<NotificationEvent>();
    
    private String message;
    
    public NotificationEvent(Object source, String message) {
        super(source);
        
        if (message == null) {
            throw new NullArgumentException("message");
        }
        
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    @Override
    public Type<NotificationEvent> getType() {
        return TYPE;
    }

}
