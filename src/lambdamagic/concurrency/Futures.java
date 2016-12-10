package lambdamagic.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * This class contains utility methods for concurrent process.
 * 
 * @author KoikeTakayuki
 */
public class Futures {

	/**
	 * Interval time taken after polling all processes.
	 * Time unit is milliseconds.
	 */
	private static int INTERVAL = 100;

	/**
	 * Produce new {@link CompletableFuture} which iterate over all the tasks
	 * in the specified collection.
	 * 
	 * New future task will be fulfilled when all the tasks in the collections are finished,
	 * and will be passed all the result.
	 * 
	 * @param tasks
	 * @return new future task which will be fulfilled when all tasks are fulfilled
	 */
	public static <T> CompletableFuture<Collection<T>> all(Collection<CompletableFuture<T>> tasks) {

		CompletableFuture<Collection<T>> future = new CompletableFuture<Collection<T>>();
		Predicate<CompletableFuture<T>> isDone = t -> t.isDone();

		CompletableFuture.runAsync(() -> {

			// ask all processes is done
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

	/**
	 * Produce new {@link CompletableFuture} which iterate over all the tasks
	 * in the specified collection.
	 * 
	 * New future task will be fulfilled when any of the tasks in the collections are finished,
	 * and will be passed the result of the one task.
	 * 
	 * When fulfilled, all the tasks in the collection is cancelled.
	 * 
	 * @param tasks
	 * @return new future task which will be fulfilled when any one of the tasks are fulfilled
	 */
	public static <T> CompletableFuture<T> any(Collection<CompletableFuture<T>> tasks) {
		CompletableFuture<T> future = new CompletableFuture<T>();

		CompletableFuture.runAsync(() -> {
			
			boolean isDone = false;

			// ask any process is done
			while (!isDone) {
				for (CompletableFuture<T> t : tasks) {
					if (t.isDone()) {
						future.complete(t.getNow(null));
						tasks.forEach(task -> {
							task.cancel(true);
						});
						isDone = true;
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