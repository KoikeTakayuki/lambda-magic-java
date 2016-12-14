package lambdamagic.collection.iterator;

import java.util.Iterator;

import lambdamagic.NullArgumentException;

/**
 * Iterator that will iterate over the values in the specified array.
 * 
 * @author KoikeTakayuki
 *
 * @param <T> Type of array element
 */
public class ArrayIterator<T> implements Iterator<T> {
	
	private T[] array;
	private int index;
	
	public ArrayIterator(T[] array) {
		if (array == null)
			throw new NullArgumentException("array");
		
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return index < array.length;
	}

	@Override
	public T next() {
		return array[index++];
	}
}

