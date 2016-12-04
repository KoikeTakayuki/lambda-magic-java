package lambdamagic.parsing;

import java.io.IOException;
import java.io.InputStream;

import lambdamagic.InvalidArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class IntegerParser implements Parser<Integer> {

	@Override
	public Either<ParseResult<Integer>, Exception> parse(InputStream inputStream, TextPosition position) {
		
		if (!inputStream.markSupported())
			throw new InvalidArgumentException("inputStream", "inputStream must support \"mark\" method");

		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);
		inputStream.mark(100);

		try {
			int c = inputStream.read();
			
			if (Characters.isEndOfStream(c))
				return Either.right(new ParseException("IntParser: EOF", position));

			if (!Characters.isDigit(c)) {			
				inputStream.reset();
				return Either.right(new ParseException("IntParser: target is not integer", position));
			}

			if (c == 0) {
				inputStream.reset();
				return Either.right(new ParseException("IntParser: first character is 0", position));
			}

			do {
				textPositionBuffer.update((char)c);
				inputStream.mark(1);
				c = inputStream.read();
			} while(Characters.isDigit(c));

			inputStream.reset();
			
			Integer i = Integer.valueOf(textPositionBuffer.getInputString());
			TextPosition newPosition = textPositionBuffer.toTextPosition();

			return Either.left(new ParseResult<Integer>(i, newPosition));

		} catch (IOException e) {
			return Either.right(e);
		}
	}
}
