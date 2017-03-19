package jp.lambdamagic.event.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import jp.lambdamagic.NullArgumentException;

public final class FileEventLog implements EventLog {

  private String filePath;
  private long maximumFileSize;
  private File file;
  
  public FileEventLog(String filePath, long maximumFileSize) {
    setFilePath(filePath);
    setMaximumFileSize(maximumFileSize);
    
    this.file = new File(filePath);
  }
  
  
  public String getFilePath() {
    return filePath;
  }

  private void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  public long getMaximumFileSize() {
    return maximumFileSize;
  }

  private void setMaximumFileSize(long maximumFileSize) {
    this.maximumFileSize = maximumFileSize;
  }
  
  @Override
  public void log(LoggedEventType type, String format, Object... args) {
    if (format == null)
      throw new NullArgumentException("format");
    
    String message = String.format("%tc [%s] %s%n", new Date(), type, String.format(format, args));
    boolean append = (getMaximumFileSize() == -1) || (file.length() + message.length()) < getMaximumFileSize();
    
    try (FileWriter writer = new FileWriter(file, append)) {
      writer.write(message);
    }
    catch (IOException ex) {
    }
  }
}
