package jp.lambdamagic.collection.iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.ArrayIterator;

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
