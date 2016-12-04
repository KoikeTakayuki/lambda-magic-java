package lambdamagic.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

import lambdamagic.InvalidArgumentException;
import lambdamagic.NullArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class TokenParser implements Parser<String> {

	private String target;
	
	public TokenParser(String target) {
		if (target == null)
			throw new NullArgumentException("target");

		this.target = target;
	}

	@Override
	public Either<ParseResult<String>, Exception> parse(InputStream inputStream, TextPosition position) {
		
		if (!inputStream.markSupported())
			throw new InvalidArgumentException("inputStream", "inputStream must support \"mark\" method");
		
		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);
		inputStream.mark(target.length());

		try {

			for (int i = 0; i < target.length(); ++i) {
				int c = inputStream.read();
				
				if (Characters.isEndOfStream(c))
					return Either.right(new ParseException(toString() + ": " + " EOF", textPositionBuffer.toTextPosition()));

				textPositionBuffer.update((char)c);
				int targetCharacter = target.charAt(i);

				if (c != targetCharacter) {
					inputStream.reset();
					return Either.right(new ParseException(toString() + ": " + " read string is " + textPositionBuffer.getInputString(), textPositionBuffer.toTextPosition()));
				}
			}

			return Either.left(new ParseResult<String>(target, textPositionBuffer.toTextPosition()));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	public String toString() {
		return "TokenParser(\"" + target + "\")";
	}
}
