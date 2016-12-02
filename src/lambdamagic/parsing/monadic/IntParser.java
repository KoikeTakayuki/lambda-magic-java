package lambdamagic.parsing.monadic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;

public class IntParser implements Parser<Integer> {

	@Override
	public Either<ParseResult<Integer>, Exception> parse(InputStream inputStream, TextPosition position) {
		return parse((PushbackInputStream)inputStream, position);
	}

	public Either<ParseResult<Integer>, Exception> parse(PushbackInputStream inputStream, TextPosition position) {

		try {
			int c = inputStream.read();
			
			if (Characters.isEndOfStream(c))
				return Either.right(new ParseException("IntParser: EOF", position));
			
			if (!Characters.isDigit(c)) {			
				inputStream.unread(c);
				return Either.right(new ParseException("IntParser: target is not integer", position));
			}

			if (c == 0) {
				inputStream.unread(c);
				return Either.right(new ParseException("IntParser: first character is 0", position));
			}

			StringBuffer sb = new StringBuffer();

			do {
				sb.append((char)c);
				c = inputStream.read();
			} while(Characters.isDigit(c));

			inputStream.unread(c);
			
			Integer i = Integer.valueOf(sb.toString());
			int length = sb.length();
			TextPosition newPosition = new TextPosition(position.getLineNumber(), position.getColumnNumber() + length);

			ParseResult<Integer> result = new ParseResult<Integer>(i, newPosition);

			return Either.left(result);

		} catch (IOException e) {
			return Either.right(e);
		}
	}
}
