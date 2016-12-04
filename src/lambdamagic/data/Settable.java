package lambdamagic.data;

/**
 * Interface that indicates given object has the state inside of it,
 * and it can be set by specifying the property of type T1 and its value of type T2.
 * Typically, type T1 is String.
 * 
 * @author KoikeTakayuki
 *
 * @param <T> type of property
 * @param <S> type of setting value
 */
public interface Settable<T, S> {

	/**
	 * Set the property of type T to the value of type S.
	 * 
	 * @param propertyName property of type T
	 * @param value setting value of type S
	 */
	void set(T propertyName, S value);
}
