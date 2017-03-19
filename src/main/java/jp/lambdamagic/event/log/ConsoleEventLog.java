package jp.lambdamagic.event.log;

import java.io.PrintStream;
import java.util.Date;

import jp.lambdamagic.NullArgumentException;

public final class ConsoleEventLog implements EventLog {

  @Override
  public void log(LoggedEventType type, String format, Object... args) {
    if (format == null) {
      throw new NullArgumentException("format");
    }
    
    @SuppressWarnings("resource")
    PrintStream output = ((type != LoggedEventType.Warning) && (type != LoggedEventType.Error))
        ? System.out : System.err;
    
    output.printf("%tc [%s] %s%n", new Date(), type, String.format(format, args));
  }
  
}
