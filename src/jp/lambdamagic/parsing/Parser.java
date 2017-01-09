package jp.lambdamagic.parsing;

@FunctionalInterface
public interface Parser<T> {
	T parse() throws Exception;
}