package lambdamagic.data;

public interface Settable<T1, T2> {

	void set(T1 propertyName, T2 value);
}
