package lambdamagic.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class Futures {
	
	private static int INTERVAL = 100;

	public static <T> CompletableFuture<Collection<T>> all(Collection<CompletableFuture<T>> tasks) {

		CompletableFuture<Collection<T>> future = new CompletableFuture<Collection<T>>();
		Predicate<CompletableFuture<T>> isDone = t -> t.isDone();

		CompletableFuture.runAsync(() -> {

			while (!tasks.stream().allMatch(isDone)) {
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException ex) {
					future.completeExceptionally(ex);
				}
			}

			Collection<T> result = new ArrayList<T>();
			
			for (CompletableFuture<T> t : tasks) {
				result.add(t.getNow(null));
			}

			future.complete(result);
		});
		
		return future;
	}
	
	public static <T> CompletableFuture<T> any(Collection<CompletableFuture<T>> tasks) {
		CompletableFuture<T> future = new CompletableFuture<T>();

		CompletableFuture.runAsync(() -> {
			
			boolean isFinish = false;
			
			while (!isFinish) {
				for (CompletableFuture<T> t : tasks) {
					if (t.isDone()) {
						future.complete(t.getNow(null));
						tasks.forEach(task -> {
							task.cancel(true);
						});
						isFinish = true;
					}
				}
				
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException ex) {
					future.completeExceptionally(ex);
				}
			}
		});

		return future;
	}
}
