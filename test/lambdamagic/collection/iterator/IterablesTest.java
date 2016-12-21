package lambdamagic.collection.iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Test;

import lambdamagic.NullArgumentException;

public class IterablesTest {

	@Test(expected=NullArgumentException.class)
	public void asIterable_mustThrowNullArgumentExceptionWhenNoElementIsGiven() {
		Iterables.asIterable(null);
	}
	
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

}
