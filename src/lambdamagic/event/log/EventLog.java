package lambdamagic.event.log;


public interface EventLog {
	void log(LoggedEventType type, String format, Object... args);
}
