package lambdamagic.parsing;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import lambdamagic.NullArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.io.EndOfStreamException;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;

public abstract class ParserBase<T> implements Parser<T>, Closeable  {

	private int c;
	private int lineNumber;
	private int lineOffset;
	private Map<String, Object> specialMap;
	private int digitCount;
	protected Reader reader;
	
	protected int getCharacter() {
		return c;
	}

	protected TextPosition getPosition() {
		return new TextPosition(lineNumber, lineOffset);
	}
	
	protected boolean isEndOfStream() throws IOException {
		return Characters.isEndOfStream(getCharacter());
	}
	
	protected boolean isEndOfLine() throws IOException {
		return Characters.isNewLine(getCharacter());
	}
	
	protected boolean isWhitespace() throws IOException {
		return Characters.isWhitespace(getCharacter());
	}
	
	protected boolean isAlphabetic() throws IOException {
		return Characters.isAlphabetic(getCharacter());
	}
	
	protected boolean isDigit() throws IOException {
		return Characters.isDigit(getCharacter());
	}

	protected boolean isValidIdFirstCharacter() throws IOException {
		return isAlphabetic();
	}
	
	protected boolean isValidNumberFirstCharacter() throws IOException {
		return isDigit() || (getCharacter() == '-');
	}

	protected boolean isValidIdCharacter() throws IOException {
		return isAlphabetic() || isDigit() || getCharacter() == '_';
	}

	protected Map<String, Object> getSpecialMap() {
		if (specialMap == null) {
			specialMap = new HashMap<String, Object>();
		}
		
		return specialMap;
	}

	public ParserBase(Reader reader) throws IOException {
		if (reader == null) {
			throw new NullArgumentException("reader");
		}
		
		this.lineNumber = 1;
		this.reader = reader;
		nextCharacter();
	}

	public ParserBase(String string) throws IOException {
		if (string == null) {
			throw new NullArgumentException("string");
		}
		
		this.reader = new StringReader(string);
		nextCharacter();
	}

	protected int readCharacter() throws IOException {
		return reader.read();
	};
	
	protected int nextCharacter() throws IOException {
		int pc = c;
		c = readCharacter();
		
		if (Characters.isNewLine(c)) {
			++lineNumber;
			lineOffset = 0;
		} else {
			++lineOffset;
		}
		return pc;
	}
	
	protected void skipWhitespacesUntilNewline() throws IOException {
		while (isWhitespace() && !isEndOfLine()) {
			nextCharacter();
		}
	}
	
	protected void skipWhitespaces() throws IOException {
		while (isWhitespace()) {
			nextCharacter();
		}
	}
	
	protected Either<String, Exception> parseUntil(char[] characters) {	
		try {

			StringBuffer sb = new StringBuffer();
			
			loop:
			while (!isEndOfStream()) {
				int c = getCharacter();
				
				for (char d : characters) {
					if (c == d) {
						break loop;
					}
				}

				sb.append((char)c);
				nextCharacter();
			}

			return Either.left(sb.toString());

		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	protected Either<String, Exception> parseId() {
		try {
			
			StringBuffer sb = new StringBuffer();

			if (!isValidIdFirstCharacter()) {
				return Either.right(new ParseException("Invalid first character of ID", getPosition()));
			}
			
			sb.append((char)nextCharacter());
			
			while (isValidIdCharacter()) {
				sb.append((char)nextCharacter());
			}
			
			return Either.left(sb.toString());
		
		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	protected Either<String, Exception> parseSpecial() {
		Either<String, Exception> parseResultOrException = parseId();
	
		if (parseResultOrException.isRight()) {
			return Either.right(parseResultOrException.getRight());
		}
		
		String key = parseResultOrException.getLeft();

		if (!getSpecialMap().containsKey(key)) {
			return Either.right(new ParseException("ID: " + key + " isn't special", getPosition()));
		}
		
		return Either.left((String)getSpecialMap().get(key));
	}
	
	public Either<Number, Exception> parseNumber() {	
		try {

			Either<Integer, Exception> integerOrException = parseInteger();

			// parsing integer part failed
			if (integerOrException.isRight()) {
				return Either.right(integerOrException.getRight());
			}
			
			// has neither decimal part nor normalized notation, finish parsing number
			if (getCharacter() != '.' && getCharacter() != 'e' && getCharacter() != 'E') {
				return integerOrException.applyToLeft(i -> (Number)i);
			}
			
			double integerPart = integerOrException.getLeft();
			double decimalPart = 0;
			double result = integerPart;

			// parsing decimal part
			if (getCharacter() == '.') {
			
				nextCharacter();
				
				Either<Integer, Exception> decimalOrException = parseUnsignedInteger();
		
				// parsing decimal part failed
				if (decimalOrException.isRight()) {
					return Either.right(decimalOrException.getRight());
				}
				
				decimalPart = decimalOrException.getLeft();
				result = integerPart + (decimalPart * Math.pow(10, -digitCount));
			}

			// parsing normalized notation
			if (getCharacter() == 'e' || getCharacter() == 'E') {
				
				nextCharacter();

				boolean isNegative = false;
				
				if (getCharacter() == '-') {
					nextCharacter();
					isNegative = true;
				} else if (getCharacter() == '+') {
					nextCharacter();
				}

				Either<Integer, Exception> exponentOrException = parseUnsignedInteger();
				
				if (exponentOrException.isRight()) {
					return Either.right(exponentOrException.getRight());
				}
				
				int exponent = exponentOrException.getLeft();
				
				if (isNegative) {
					exponent *= -1;
				}
				
				result = result * Math.pow(10, exponent);
			}
	
			return Either.left(result);
			
		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	public Either<Integer, Exception> parseInteger() {
		
		try {
			boolean isNegative = false;
			
			if (getCharacter() == '-') {
				isNegative = true;
				nextCharacter();
			}
			
			Either<Integer, Exception> integerOrException = parseUnsignedInteger();

			if (isNegative) {
				return integerOrException.applyToLeft(i -> -i);
			}
			
			return integerOrException;

		} catch (IOException e) {
			return Either.right(e);
		}
	}

	private Either<Integer, Exception> parseUnsignedInteger() {
		
		try {
			int value = 0;
	
			digitCount = 0;
			
			int c;
			while (isDigit()) {
				c = nextCharacter();
				value = value * 10 + (c - '0');
				++digitCount;
			}
			
			return Either.left(value);

		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	protected Either<String, Exception> parseString(char delimiterCharacter, char escapeCharacter) {
		
		try {
			if (getCharacter() != delimiterCharacter)
				return Either.right(new ParseException("Try to parse string expression, but first character isn't delimeter character " + getCharacter(), getPosition()));

			nextCharacter();
	
			StringBuffer sb = new StringBuffer();
	
			while (getCharacter() != delimiterCharacter) {

				if (isEndOfStream()) {
					return Either.right(new EndOfStreamException());
				}
				
				if (getCharacter() == escapeCharacter) {
					nextCharacter();
	
					int lookahead = getCharacter();
					if (lookahead == delimiterCharacter) {
						sb.append(delimiterCharacter);
						nextCharacter();
					} else if (lookahead == escapeCharacter) {
						sb.append(escapeCharacter);
						nextCharacter();
					} else if (lookahead == 't') {
						sb.append('\t');
						nextCharacter();
					} else if (lookahead == 'r') {
						sb.append('\r');
						nextCharacter();
					} else if (lookahead == 'n') {
						sb.append('\n');
						nextCharacter();
					} else {
						sb.append(escapeCharacter);
						sb.append((char)lookahead);
					}
					
				} else {
					sb.append((char)getCharacter());
					nextCharacter();
				}
			}
	
			nextCharacter();
			
			return Either.left(sb.toString());

		} catch (IOException e) {
			return Either.right(e);
		}
	}
	
	public void close() throws IOException {
		reader.close();
	}

}
