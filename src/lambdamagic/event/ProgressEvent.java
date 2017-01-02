package lambdamagic.event;

public class ProgressEvent extends Event<ProgressEventListener> {

	public static final Type<ProgressEventListener> TYPE = new Type<ProgressEventListener>();
	
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
	public Type<ProgressEventListener> getType() {
		return TYPE;
	}

	@Override
	public void dispatch(ProgressEventListener listener) {
		listener.onProgressChanged(this);
	}
	
}
