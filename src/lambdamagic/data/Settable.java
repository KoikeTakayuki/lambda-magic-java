package lambdamagic.data;

public interface Settable<T, S> {
	void set(T propertyName, S value);
}
