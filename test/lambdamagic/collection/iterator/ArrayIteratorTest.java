package lambdamagic.collection.iterator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import lambdamagic.NullArgumentException;

public class ArrayIteratorTest {

	@Test(expected=NullArgumentException.class)
	public void ArrayIterator_mustThrowNullArgumentExceptionWhenNullArrayIsGiven() {
		new ArrayIterator<Object>(null);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void next_mustThrowArrayIndexOutOfBoundsExceptionWhenItDoesNotHaveNextElement() {
		ArrayIterator<Integer> it = new ArrayIterator<Integer>(new Integer[] { 1 });

		assertThat(it.hasNext(), is(true));
		it.next();
		assertThat(it.hasNext(), is(false));
		it.next();
	}

	@Test
	public void ArrayIterator_properlyIterateGivenArray() {
		ArrayIterator<Integer> it = new ArrayIterator<Integer>(new Integer[] { 1, 2, 3 });
		
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(1));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(2));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(3));
		assertThat(it.hasNext(), is(false));
	}

}