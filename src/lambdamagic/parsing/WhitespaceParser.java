package lambdamagic.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import lambdamagic.InvalidArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.text.Characters;
import lambdamagic.text.Strings;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class WhitespaceParser implements Parser<String> {

	@Override
	public Either<ParseResult<String>, Exception> parse(Reader reader, TextPosition position) {

		if (!reader.markSupported())
			throw new InvalidArgumentException("inputStream", "inputStream must support \"mark\" method");

		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);

		try {
			int c = reader.read();
	
			if (Characters.isEndOfStream(c))
				return Either.left(new ParseResult<String>(Strings.EMPTY_STRING, textPositionBuffer.toTextPosition()));

			if (!Characters.isWhitespace(c)) {
				reader.reset();
				return Either.left(new ParseResult<String>(Strings.EMPTY_STRING, textPositionBuffer.toTextPosition()));
			}

			do {
				textPositionBuffer.update((char)c);
				reader.mark(1);
				c = reader.read();
			} while(Characters.isWhitespace(c));

			reader.reset();
			
			String s = textPositionBuffer.getInputString();
			TextPosition newPosition = textPositionBuffer.toTextPosition();

			return Either.left(new ParseResult<String>(s, newPosition));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

}
