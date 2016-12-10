package lambdamagic.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import lambdamagic.data.functional.Either;
import lambdamagic.io.EndOfStreamException;
import lambdamagic.parsing.ParserBase;

public class CSVParser extends ParserBase<List<String>> {

	public CSVParser(Reader reader) throws IOException {
		super(reader);
		ignoreBOM();
	}

	@Override
	public Either<List<String>, Exception> parse() {
		return parseRow();
	}

	private Either<List<String>, Exception> parseRow() {
		try {

			if (isEndOfStream())
				return Either.right(new EndOfStreamException());

			List<String> result = new ArrayList<String>();

			skipWhitespacesUntilNewline();

			//empty row
			if (isEndOfLine()) {
				nextCharacter();
				return Either.left(result);
			}

			// parsing first element
			Either<String, Exception> valueOrException = parseCSVValue();

			// parsing first element failed
			if (valueOrException.isRight())
				return Either.right(valueOrException.getRight());

			result.add(valueOrException.getLeft());

			// parsing remaining element
			while (true) {

				skipWhitespacesUntilNewline();

				if (isEndOfStream())
					break;

				if (isEndOfLine()) {
					nextCharacter();
					break;
				}

				if (getCharacter() != CSVSpecialCharacter.VALUE_SEPARATOR_CHAR)
					return Either.right(new CSVFormatException(getPosition()));

				nextCharacter();
				skipWhitespacesUntilNewline();

				if (isEndOfLine()) {
					nextCharacter();
					break;
				}

				valueOrException = parseCSVValue();

				if (valueOrException.isRight())
					return Either.right(valueOrException.getRight()); 
				
				result.add(valueOrException.getLeft());
			}

			return Either.left(result);

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	public Either<String, Exception> parseCSVValue() {
		if (getCharacter() == CSVSpecialCharacter.VALUE_DELIMITER_CHAR)
			return parseCSVString();

		return parseUntil(CSVSpecialCharacter.DEFAULT_VALUE_SEPARATORS);
	}

	private Either<String, Exception> parseCSVString() {
		try {

			nextCharacter();
			StringBuffer sb = new StringBuffer();
	
			while (true) {

				if (isEndOfStream()) {
					return Either.right(new EndOfStreamException());
				}
				
				if (getCharacter() == CSVSpecialCharacter.VALUE_ESCAPE_CHAR) {
					nextCharacter();
	
					int lookahead = getCharacter();

					if (lookahead == CSVSpecialCharacter.VALUE_ESCAPE_CHAR) {
						sb.append(CSVSpecialCharacter.VALUE_ESCAPE_CHAR);
						nextCharacter();
					}
					else if (lookahead == 't') {
						sb.append('\t');
						nextCharacter();
					}
					else if (lookahead == 'r') {
						sb.append('\r');
						nextCharacter();
					}
					else if (lookahead == 'n') {
						sb.append('\n');
						nextCharacter();
					}
					else {
						sb.append(CSVSpecialCharacter.VALUE_ESCAPE_CHAR);
						sb.append((char)lookahead);
					}
					
				// double quote escaping or end of string
				} else if (getCharacter() == CSVSpecialCharacter.VALUE_DELIMITER_CHAR) {
					nextCharacter();

					//double quote escaping
					if (getCharacter() == CSVSpecialCharacter.VALUE_DELIMITER_CHAR) {
						sb.append(CSVSpecialCharacter.VALUE_DELIMITER_CHAR);
						nextCharacter();
					//end of string
					} else {
						return Either.left(sb.toString());
					}

				} else {
					sb.append((char)getCharacter());
					nextCharacter();
				}
			}

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	private void ignoreBOM() throws IOException {
		reader.mark(4);
		if (reader.read() != '\ufeff')
			reader.reset(); 
	}

}