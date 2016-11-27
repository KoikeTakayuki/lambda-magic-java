package lambdamagic.pipeline;

@FunctionalInterface
public interface DataProcessor<T1, T2> {
	T2 process(T1 data);
}