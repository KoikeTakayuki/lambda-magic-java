package lambdamagic.parsing.combinator;

import java.io.Reader;
import java.util.function.Function;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class ParserMapping<T, U> implements Parser<U> {
	
	private Parser<T> wrapped;
	private Function<T, U> mapping;
	
	public ParserMapping(Parser<T> wrapped, Function<T, U> mapping) {
		this.wrapped = wrapped;
		this.mapping = mapping;
	}

	@Override
	public Either<ParseResult<U>, Exception> parse(Reader reader, TextPosition position) {
		return wrapped.parse(reader, position).applyToLeft(left -> {
			U mappingResult = mapping.apply(left.getResult());
			return new ParseResult<U>(mappingResult, left.getPosition());
		});
	}
}
