package jp.lambdamagic.data;

public interface Cancellable {
	boolean isCancelled();
	void cancel();
}
