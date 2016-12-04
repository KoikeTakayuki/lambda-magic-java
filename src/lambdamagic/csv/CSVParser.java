package lambdamagic.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class CSVParser implements Parser<List<String>> {
	
	private int character;

	@Override
	public Either<ParseResult<List<String>>, Exception> parse(InputStream inputStream, TextPosition position) {
		TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);

		try {
			nextCharacter(inputStream, textPositionBuffer);

			if (Characters.isEndOfStream(character))
				return Either.right(new Exception("CSVParser: EOF"));
			
			List<String> result = new ArrayList<String>();

			if (isEndOfRow())
				return Either.left(new ParseResult<List<String>>(result, textPositionBuffer.toTextPosition()));

			while (!isEndOfRow() && !isEndOfStream()) {
				Either<ParseResult<String>, Exception> parseResultOrException;

				if (character == CSVSpecialCharacter.VALUE_DELIMITER_CHAR)
					parseResultOrException = parseCSVString(inputStream, textPositionBuffer);
				else
					parseResultOrException = parseCSVValue(inputStream, textPositionBuffer);


				if (parseResultOrException.isLeft()) {
					result.add(parseResultOrException.getLeft().getResult());
				} else {
					Exception e = parseResultOrException.getRight();
					return Either.right(new Exception("CSVParser: Exception has occured while parsing row", e));
				}
			}

			return Either.left(new ParseResult<List<String>>(result, textPositionBuffer.toTextPosition()));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	private Either<ParseResult<String>, Exception> parseCSVValue(InputStream inputStream,
			TextPositionBuffer textPositionBuffer) {

		StringBuffer sb = new StringBuffer();

		try {
			while (!isEndOfRow() && !isEndOfStream() && !isValueSeparator()) {
				sb.append((char)character);
				character = inputStream.read();
				textPositionBuffer.update((char)character);
			}

			if (isValueSeparator())
				nextCharacter(inputStream, textPositionBuffer);

			return Either.left(new ParseResult<String>(sb.toString(), textPositionBuffer.toTextPosition()));
		} catch (IOException e) {
			return Either.right(e);
		}
	}

	private Either<ParseResult<String>, Exception> parseCSVString(InputStream inputStream,
			TextPositionBuffer textPositionBuffer) {
		
		StringBuffer sb = new StringBuffer();

		try {
			while (!isEndOfStream()) {
	
				character = inputStream.read();
				textPositionBuffer.update((char)character);
				if (isValueDelemeter()) {

					//lookahead
					nextCharacter(inputStream, textPositionBuffer);

					if (!isValueDelemeter()) {
						if (!isEndOfRow() && !isEndOfStream() && !isValueSeparator())
							return Either.right(new CSVFormatException(textPositionBuffer.toTextPosition()));

						nextCharacter(inputStream, textPositionBuffer);
						return Either.left(new ParseResult<String>(sb.toString(), textPositionBuffer.toTextPosition()));
					}
				}

				sb.append((char)character);
			}

			return Either.right(new CSVFormatException(textPositionBuffer.toTextPosition()));
		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	private void nextCharacter(InputStream inputStream, TextPositionBuffer textPositionBuffer) throws IOException {
		int c;
		do {
			c = inputStream.read();
			textPositionBuffer.update((char)c);
		} while (c == Characters.SPACE || c == Characters.TAB);

		character = c;
	}

	private boolean isEndOfRow() {
		return (char)character == CSVSpecialCharacter.ROW_SEPARATOR_CHAR;
	}
	
	private boolean isEndOfStream() {
		return Characters.isEndOfStream(character);
	}
	
	private boolean isValueDelemeter() {
		return (char)character == CSVSpecialCharacter.VALUE_DELIMITER_CHAR;
	}
	
	private boolean isValueSeparator() {
		return (char)character == CSVSpecialCharacter.VALUE_SEPARATOR_CHAR;
	}

}