package lambdamagic.data;

/**
 * An interface for transforming an value of type T1 to type T2.
 * 
 * @author KoikeTakayuki
 *
 * @param <T1> input type of object
 * @param <T2> output type of object
 */
public interface DataTransformer<T1, T2> {

	/**
	 * Returns the value of type T2 from the value of type T1
	 * 
	 * @param input a object of type T1
	 * @return a value of type T2
	 */
	T2 transform(T1 input);
}