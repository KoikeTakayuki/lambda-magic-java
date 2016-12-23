package lambdamagic.collection.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import lambdamagic.NullArgumentException;

public final class Iterables {

	
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> asIterable(final T... elements) {
		if (elements == null)
			throw new NullArgumentException("elements");
		
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new ArrayIterator<T>(elements);
			}
		};
	}
	
	public static <T, S> Iterable<S> map(Iterable<T> elements, Function<T, S> function) {
		if (elements == null)
			throw new NullArgumentException("elements");
		
		if (function == null)
			throw new NullArgumentException("function");
		
		List<S> result = new ArrayList<S>();

		for (T e : elements) {
			result.add(function.apply(e));
		}
		
		return result;
	}
	
	public static <T> Iterable<T> filter(Iterable<T> elements, Predicate<T> predicate) {
		if (elements == null)
			throw new NullArgumentException("elements");
		
		if (predicate == null)
			throw new NullArgumentException("predicate");
		
		List<T> result = new ArrayList<T>();

		for (T e : elements) {
			if (predicate.test(e))
				result.add(e);
		}
		
		return result;
	}
	
	public static <T, S> S foldLeft(Iterable<T> elements, S seed, BiFunction<S, T, S> foldingFunction) {
		if (elements == null)
			throw new NullArgumentException("elements");
		
		if (seed == null)
			throw new NullArgumentException("seed");
		
		if (foldingFunction == null)
			throw new NullArgumentException("foldingFunction");
		
		S result = seed;

		for (T e : elements) {
			result = foldingFunction.apply(result, e);
		}
		
		return result;
	}

	public static <I, O> Function<Iterable<I>, Iterable<O>> lift(Function<I, O> function) {
		
		if (function == null)
			throw new NullArgumentException("function");

		return (Iterable<I> iterable) -> {
			List<O> result = new ArrayList<O>();

			for (I e : iterable)
				result.add(function.apply(e));

			return result;
		};
	}
	
	private Iterables() {}
}
