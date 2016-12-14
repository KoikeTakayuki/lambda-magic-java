package lambdamagic.data;

import java.util.Optional;

/**
 * An interface that indicates given object receive the
 * query (that can be failed) of type T1 and return the value of type {@link Optional}<T2>.
 * 
 * If result exists, return that value of Type T2 with the {@link Optional} context.
 * otherwise return {@code Optional.empty()}.
 * 
 * Typically, type T1 is String.
 * 
 * @author KoikeTakayuki
 *
 * @param <T1> query type
 * @param <T2> output type of query
 */
public interface Queryable<T1, T2> {

	/**
	 * Receive the query of type T1 and return the value of type {@link Optional}<T2>.
	 * If query is failed, return {@code Optional.empty()}.
	 * 
	 * @param propertyName query of type T1 that specifies the property name
	 * @return output of type T2 wrapped in the {@link Optional} Context
	 */
	Optional<T2> get(T1 propertyName);
}
