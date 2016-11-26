package lambdamagic.data;

public interface DataTransformer<T1, T2> {

	T2 transform(T1 input);
}
