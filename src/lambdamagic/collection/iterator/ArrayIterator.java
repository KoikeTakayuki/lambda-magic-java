package lambdamagic.collection.iterator;

import java.util.Iterator;


/**
 * Iterator that will iterate over the values in the specified array.
 * 
 * @author KoikeTakayuki
 *
 * @param <T> Type of Array element
 */
public class ArrayIterator<T> implements Iterator<T> {
	
	private T[] array;
	private int index;

	@Override
	public boolean hasNext() {
		return index < array.length;
	}

	@Override
	public T next() {
		return array[index++];
	}
}

