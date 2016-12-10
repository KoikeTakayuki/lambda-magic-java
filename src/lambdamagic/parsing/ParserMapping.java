package lambdamagic.parsing;

import java.util.function.Function;

import lambdamagic.data.functional.Either;

public class ParserMapping<T, U> implements Parser<U> {
	
	private Parser<T> wrapped;
	private Function<T, U> mapping;
	
	public ParserMapping(Parser<T> wrapped, Function<T, U> mapping) {
		this.wrapped = wrapped;
		this.mapping = mapping;
	}

	@Override
	public Either<U, Exception> parse() {
		return wrapped.parse().applyToLeft(mapping);
	}
}
