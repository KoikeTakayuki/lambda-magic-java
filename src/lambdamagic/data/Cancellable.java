package lambdamagic.data;

public interface Cancellable {

	boolean isCancelled();
	
	void cancel();
}
