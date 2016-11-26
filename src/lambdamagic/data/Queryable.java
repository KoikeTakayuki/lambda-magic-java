package lambdamagic.data;

public interface Queryable<T1, T2> {
	
	T2 get(T1 propertyName);
}
