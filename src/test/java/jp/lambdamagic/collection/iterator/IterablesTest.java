package jp.lambdamagic.collection.iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.function.Function;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;

public class IterablesTest {

	@Test
	public void asIterable_convertElementsToIterable() {
		Iterable<Integer> iterable = Iterables.asIterable(1, 2, null);
		Iterator<Integer> iterator = iterable.iterator();
		
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(nullValue()));
		assertThat(iterator.hasNext(), is(false));
	}

	@Test(expected=NullArgumentException.class)
	public void map_mustThrowNullArgumentExceptionWhenNullElementsIsGiven() {
		Iterables.map(null, Function.identity());
	}
	
	@Test(expected=NullArgumentException.class)
	public void map_mustThrowNullArgumentExceptionWhenNullFunctionIsGiven() {
		Iterables.map(Iterables.asIterable("test"), null);
	}
	
	@Test
	public void map_mapIterableToAnotherIterable() {
		Iterable<Integer> newIterable = Iterables.map(Iterables.asIterable(1, 2, 3), number -> number + 1);
		
		int counter = 2;
		for (int e : newIterable) {
			assertThat(e, is(counter));
			++counter;
		}
	}
	
	@Test(expected=NullArgumentException.class)
	public void filter_mustThrowNullArgumentExceptionWhenNullElementsIsGiven() {
		Iterables.filter(null, e -> true);
	}

	@Test(expected=NullArgumentException.class)
	public void filter_mustThrowNullArgumentExceptionWhenNullPredicateIsGiven() {
		Iterables.filter(Iterables.asIterable("test"), null);
	}
	
	@Test
	public void filter_filterIterableWithPredicate() {
		Iterable<Integer> newIterable = Iterables.filter(Iterables.asIterable(1, 2, 3, 4, 5), number -> (number % 2 == 1));
		
		int counter = 1;
		for (int e : newIterable) {
			assertThat(e, is(counter));
			counter += 2;
		}
	}

	@Test(expected=NullArgumentException.class)
	public void foldLeft_mustThrowNullArgumentExceptionWhenNullElementsIsGiven() {
		Iterables.foldLeft(null, 1, (acc, e) -> 1);
	}

	@Test(expected=NullArgumentException.class)
	public void foldLeft_mustThrowNullArgumentExceptionWhenNullSeedIsGiven() {
		Iterables.foldLeft(Iterables.asIterable("test"), null, (acc, e) -> e);
	}

	@Test(expected=NullArgumentException.class)
	public void foldLeft_mustThrowNullArgumentExceptionWhenNullFoldingFunctionIsGiven() {
		Iterables.foldLeft(Iterables.asIterable("test"), 1, null);
	}
	
	@Test
	public void foleLeft_foldIterableWithFoldingFunction() {
		int result = Iterables.foldLeft(Iterables.asIterable("test1", "test2", "test3"), 0, (acc, e) -> acc + e.length());
		assertThat(result, is(15));
	}
	
	@Test(expected=NullArgumentException.class)
	public void lift_mustThrowNullArgumentExceptionWhenNullFunctionIsGiven() {
		Iterables.lift(null);
	}

	@Test
	public void lift_liftUpGivenFunctionToAnotherFunctionWhichIsApplicableForIterable() {
		Function<Iterable<String>, Iterable<Integer>> function = Iterables.lift((String string) -> string.length());
		Iterable<Integer> result = function.apply(Iterables.asIterable("test", "test1", "test11", "test111"));
		
		int counter = 4;
		for (int e : result) {
			assertThat(e, is(counter));
			++counter;
		}
	}
	
}
