package lambdamagic.collection.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import lambdamagic.NullArgumentException;
import lambdamagic.pipeline.DataProcessor;

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

	public static <T> List<T> asList(Iterable<T> iterable) {
		if (iterable == null)
			throw new NullArgumentException("iterable");
		
		if (iterable instanceof List<?>)
			return (List<T>)iterable;
		
		List<T> list = new ArrayList<T>();
		for (T t : iterable)
			list.add(t);
		
		return list;
	}

	public static <I, O, T extends Iterable<I>>
	DataProcessor<T, List<O>> lift(Function<I, O> function) {

		return (T list) -> {
			List<O> result = new ArrayList<O>();

			for (I e : list)
				result.add(function.apply(e));

			return result;
		};
	}
	
	private Iterables() {}
}
