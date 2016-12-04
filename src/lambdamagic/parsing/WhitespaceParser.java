package lambdamagic.parsing;

import java.io.IOException;
import java.io.InputStream;

import lambdamagic.InvalidArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.text.Characters;
import lambdamagic.text.Strings;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class WhitespaceParser implements Parser<String> {

	@Override
	public Either<ParseResult<String>, Exception> parse(InputStream inputStream, TextPosition position) {

		if (!inputStream.markSupported())
			throw new InvalidArgumentException("inputStream", "inputStream must support \"mark\" method");

		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);

		try {
			int c = inputStream.read();
	
			if (Characters.isEndOfStream(c))
				return Either.left(new ParseResult<String>(Strings.EMPTY_STRING, textPositionBuffer.toTextPosition()));

			if (!Characters.isWhitespace(c)) {
				inputStream.reset();
				return Either.left(new ParseResult<String>(Strings.EMPTY_STRING, textPositionBuffer.toTextPosition()));
			}

			do {
				textPositionBuffer.update((char)c);
				inputStream.mark(1);
				c = inputStream.read();
			} while(Characters.isWhitespace(c));

			inputStream.reset();
			
			String s = textPositionBuffer.getInputString();
			TextPosition newPosition = textPositionBuffer.toTextPosition();

			return Either.left(new ParseResult<String>(s, newPosition));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

}
