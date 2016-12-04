package lambdamagic.data;

import java.util.function.Function;

/**
 * An interface for transforming an value of type T1 to type T2.
 * 
 * @author KoikeTakayuki
 *
 * @param <I> input type
 * @param <O> output type
 */
@FunctionalInterface
public interface DataTransformer<I, O> extends Function<I, O> {

	/**
	 * Returns the value of type O from the value of type I
	 * 
	 * @param input an object of type T1
	 * @return a value of type T2
	 */
	O transform(I input);

	@Override
	default O apply(I input) {
		return transform(input);
	}
}