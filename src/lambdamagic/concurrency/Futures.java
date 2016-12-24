package lambdamagic.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import lambdamagic.NullArgumentException;
import lambdamagic.collection.iterator.Iterables;
import lambdamagic.data.Tuple2;

public class Futures {
	
	public static <T> CompletableFuture<T> runAsync(Supplier<T> task) {
		if (task == null)
			throw new NullArgumentException("task");

		return CompletableFuture.supplyAsync(task);
	}
	
	public static CompletableFuture<Void> runAsync(Runnable task) {
		if (task == null)
			throw new NullArgumentException("task");

		return runAsync(() -> task.run());
	}
	
	public static <I, O> Function<CompletableFuture<I>, CompletableFuture<O>> lift(Function<I, O> function) {
		if (function == null)
			throw new NullArgumentException("function");
		
		return inputFuture -> inputFuture.thenApply(function);
	}
	
	public static <T> CompletableFuture<T> wrap(T value) {
		if (value == null)
			throw new NullArgumentException("value");
		
		return CompletableFuture.supplyAsync(() -> value);
	}

	public static <T> CompletableFuture<List<T>> all(Iterable<CompletableFuture<T>> tasks) {
		if (tasks == null)
			throw new NullArgumentException("tasks");
		
		return Iterables.foldLeft(tasks, wrap(Arrays.asList()), (acc, e) -> {
			return acc.thenCombineAsync(e, (values, value) -> {
				List<T> newValues = new ArrayList<T>();
				newValues.addAll(values);
				newValues.add(value);
				return newValues;
			});
		});
	}

	public static <T> CompletableFuture<T> any(Iterable<CompletableFuture<T>> tasks) {
		if (tasks == null)
			throw new NullArgumentException("tasks");
		
		return Iterables.reduce(tasks, (future1, future2) -> {
			return future1.applyToEitherAsync(future2, Function.identity());
		});
	}
	
	public static <T, S> CompletableFuture<Tuple2<T, S>> join(CompletableFuture<T> task1, CompletableFuture<S> task2) {
		if (task1 == null)
			throw new NullArgumentException("task1");
		
		if (task2 == null)
			throw new NullArgumentException("task2");
		
		return task1.thenCombineAsync(task2, (t1, t2) -> new Tuple2<T, S>(t1, t2));
	}
}