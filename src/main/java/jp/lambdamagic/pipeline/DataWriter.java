package jp.lambdamagic.pipeline;

@FunctionalInterface
public interface DataWriter<T> extends DataProcessor<T, T> {
	
	void write(T data) throws Exception;
	
	@Override
	default T process(T data) throws Exception {
		write(data);
		return data;
	}
	
}
