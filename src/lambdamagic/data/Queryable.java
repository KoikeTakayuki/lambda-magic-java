package lambdamagic.data;

/**
 * An interface that indicates given object receive the
 * query of type T1 and return the value of type T2 depending on the query.
 * Typically, type T1 is String.
 * 
 * @author koiketakayuki
 *
 * @param <T1> query type
 * @param <T2> output type of query
 */
public interface Queryable<T1, T2> {

	/**
	 * Receive the query of type T1 and return the value of type T2.
	 * 
	 * @param propertyName query of type T1 that specifies the property name
	 * @return output of type T2
	 */
	T2 get(T1 propertyName);
}
