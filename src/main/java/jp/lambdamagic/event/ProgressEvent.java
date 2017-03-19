package jp.lambdamagic.event;

public class ProgressEvent extends Event {
  
  public static final Type<ProgressEvent> TYPE = new Type<ProgressEvent>();
  
  private long count;
  private long totalCount;
  
  public ProgressEvent(Object source, long count, long totalCount) {
    super(source);
    
    this.count = count;
    this.totalCount = totalCount;
  }
  
  public float getPercentage() {
    return (count * 100.0f / (float)totalCount);
  }
  
  public long getCount() {
    return count;
  }
  
  public long getTotalCount() {
    return totalCount;
  }
  
  @Override
  public Type<ProgressEvent> getType() {
    return TYPE;
  }

}
