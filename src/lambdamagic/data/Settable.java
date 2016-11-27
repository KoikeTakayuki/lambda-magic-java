package lambdamagic.data;

/**
 * Interface that indicates given object has the state inside of it,
 * and it can be set by specifying the property of type T1 and its value of type T2.
 * Typically, type T1 is String.
 * 
 * @author koiketakayuki
 *
 * @param <T1> type of property
 * @param <T2> type of setting value
 */
public interface Settable<T1, T2> {

	/**
	 * Set the property of type T1 to the value of type T2.
	 * 
	 * @param propertyName property of type T1
	 * @param value setting value of type T2
	 */
	void set(T1 propertyName, T2 value);
}
