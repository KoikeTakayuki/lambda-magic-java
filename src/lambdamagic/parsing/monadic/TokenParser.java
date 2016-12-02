package lambdamagic.parsing.monadic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;

public class TokenParser implements Parser<String> {

	private String target;
	
	public TokenParser(String target) {
		this.target = target;
	}
	
	@Override
	public Either<ParseResult<String>, Exception> parse(InputStream inputStream, TextPosition position) {
		return parse((PushbackInputStream)inputStream, position);
	}
	
	public Either<ParseResult<String>, Exception> parse(PushbackInputStream inputStream, TextPosition position) {

		try {

			byte[] buffer = new byte[target.length()];

			for (int i = 0; i < target.length(); ++i) {
				int c = inputStream.read();
				buffer[i] = (byte)c;
				int targetCharacter = target.charAt(i);

				if (Characters.isEndOfStream(c))
					return Either.right(new ParseException(toString() + ": " + " EOF", position));

				if (c != targetCharacter) {
					inputStream.unread(Arrays.copyOf(buffer, i + 1));
					return Either.right(new ParseException(toString() + ": " + " result is " + new String(buffer), position));
				}
			}

			TextPosition newPosition = new TextPosition(position.getLineNumber(), position.getColumnNumber() + target.length());

			return Either.left(new ParseResult<String>(target, newPosition));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	public String toString() {
		return "TokenParser(\"" + target + "\")";
	}
}
