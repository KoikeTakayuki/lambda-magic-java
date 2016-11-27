package lambdamagic.data;

/**
 * An interface that indicates that given object
 * represents some process which can be cancelled.
 * 
 * @author KoikeTakayuki
 */
public interface Cancellable {

	/**
	 * Check whether or not the process is cancelled.
	 * 
	 * @return true if cancelled, false if not 
	 */
	boolean isCancelled();
	
	/**
	 * Cancel the process represented by the object.
	 */
	void cancel();
}
