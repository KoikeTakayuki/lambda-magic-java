package lambdamagic.parsing;

import java.io.IOException;
import java.io.Reader;

import lambdamagic.InvalidArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.io.EndOfStreamException;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class IntegerParser implements Parser<Integer> {

	@Override
	public Either<ParseResult<Integer>, Exception> parse(Reader reader, TextPosition position) {
		
		if (!reader.markSupported())
			throw new InvalidArgumentException("inputStream", "inputStream must support \"mark\" method");

		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);
		

		try {
			reader.mark(100);
			int c = reader.read();
			
			if (Characters.isEndOfStream(c))
				return Either.right(new EndOfStreamException());

			if (!Characters.isDigit(c)) {			
				reader.reset();
				return Either.right(new ParseException("IntParser: target is not integer", position));
			}

			if (c == 0) {
				reader.reset();
				return Either.right(new ParseException("IntParser: first character is 0", position));
			}

			do {
				textPositionBuffer.update((char)c);
				reader.mark(1);
				c = reader.read();
			} while(Characters.isDigit(c));

			reader.reset();
			
			Integer i = Integer.valueOf(textPositionBuffer.getInputString());
			TextPosition newPosition = textPositionBuffer.toTextPosition();

			return Either.left(new ParseResult<Integer>(i, newPosition));

		} catch (IOException e) {
			return Either.right(e);
		}
	}
}
