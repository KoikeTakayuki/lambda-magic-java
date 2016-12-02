package lambdamagic.parsing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;

public abstract class SimpleParser<T> implements Parser<T>{
	
	protected static final int END_OF_STREAM_CODE = -1;

	private int c;
	private int lineNumber;
	private int lineOffset;

	private int digitCount;
	private Object value;
	private Map<String, Object> specialMap;

	public int getCharacter() {
		return c;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getLineOffset() {
		return lineOffset;
	}

	public TextPosition getPosition() {
		return new TextPosition(lineNumber, lineOffset);
	}
	
	public boolean isEndOfStream() throws IOException {
		return Characters.isEndOfStream(getCharacter());
	}
	
	public boolean isEndOfLine() throws IOException {
		return Characters.isNewLine(getCharacter());
	}
	
	public boolean isWhitespace() throws IOException {
		return Characters.isWhitespace(getCharacter());
	}
	
	public boolean isAlphabetic() throws IOException {
		return Characters.isAlphabetic(getCharacter());
	}
	
	public boolean isDigit() throws IOException {
		return Characters.isDigit(getCharacter());
	}
	
	public boolean isValidIdFirstCharacter() throws IOException {
		return isAlphabetic();
	}
	
	public boolean isValidIdCharacter() throws IOException {
		return isAlphabetic() || isDigit() || getCharacter() == '_';
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public Map<String, Object> getSpecialMap() {
		if (specialMap == null)
			specialMap = new HashMap<String, Object>();
		
		return specialMap;
	}
	
	public SimpleParser() {
		this.lineNumber = 1;
	}
	
	public int nextCharacter() throws IOException {
		int pc = c;
		c = readCharacter();

		if (Characters.isNewLine(c)) {
			++lineNumber;
			lineOffset = 0;
		}
		else
			++lineOffset;

		return pc;
	}

	protected abstract int readCharacter() throws IOException;

	public void skipWhitespacesUntilNewline() throws IOException {
		while (isWhitespace() && !isEndOfLine())
			nextCharacter();
	}

	public void skipWhitespaces() throws IOException {
		while (isWhitespace())
			nextCharacter();
	}
	
	public boolean parseUntil(char[] characters) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		loop:
		while (!isEndOfStream()) {
			int c = getCharacter();
			for (char d : characters)
				if (c == d)
					break loop;
			
			sb.append((char)c);
			nextCharacter();
		}
		
		setValue(sb.toString());
		return true;
	}
	
	public boolean parseId() throws IOException {
		StringBuffer sb = new StringBuffer();
		
		if (!isValidIdFirstCharacter())
			return false;
		
		sb.append((char)nextCharacter());
		
		while (isValidIdCharacter())
			sb.append((char)nextCharacter());
		
		setValue(sb.toString());
		return true;
	}
	
	public boolean parseValue(char stringDelimiterCharacter, char escapeCharacter) throws IOException {
		if (isDigit() || (getCharacter() == '-'))
			return parseNumber();
		
		if (getCharacter() == stringDelimiterCharacter)
			return parseString(stringDelimiterCharacter, escapeCharacter);
		
		return parseSpecial();
	}
	
	public boolean parseSpecial() throws IOException {
		if (!parseId())
			return false;
		
		String value = (String)getValue();
	
		if (!getSpecialMap().containsKey(value))
			return false;
		
		setValue(getSpecialMap().get(value));
		return true;
	}
	
	public boolean parseNumber() throws IOException {
		if (!parseInteger())
			return false;
		
		if (getCharacter() != '.')
			return true;
		
		nextCharacter();
		
		int integerPart = (Integer)getValue();
		
		if (!parseUnsignedInteger())
			return false;
		
		long decimalPart = (Long)getValue();
		
		double value = (double)integerPart + ((double)decimalPart * Math.pow(10, -digitCount));
		
		setValue(value);
		return true;
	}
	
	public boolean parseInteger() throws IOException {
		boolean isNegative = false;
		
		if (getCharacter() == '-') {
			isNegative = true;
			nextCharacter();
		}

		if (!parseUnsignedInteger())
			return false;
		
		int value = ((Number)getValue()).intValue();
		setValue(isNegative ? -value : value);
		return true;
	}
	
	public boolean parseUnsignedInteger() throws IOException {
		long value = 0;

		digitCount = 0;
		
		int c;
		while (isDigit()) {
			c = nextCharacter();
			value = value * 10 + (c - '0');
			++digitCount;
		}
		
		setValue(value);
		return true;
	}
	
	public boolean parseString(char delimiterCharacter, char escapeCharacter) throws IOException {
		if (getCharacter() != delimiterCharacter)
			return false;
		
		nextCharacter();
		
		StringBuffer sb = new StringBuffer();
		
		int c;
		while ((c = getCharacter()) != delimiterCharacter) {
			
			if (c == END_OF_STREAM_CODE)
				return false;
			
			if (c == escapeCharacter) {
				nextCharacter();
				
				int sc = getCharacter();
				if (sc == delimiterCharacter) {
					sb.append(delimiterCharacter);
					nextCharacter();
				}
				else if (sc == escapeCharacter) {
					sb.append(escapeCharacter);
					nextCharacter();
				}
				else if (sc == 't') {
					sb.append('\t');
					nextCharacter();
				}
				else if (sc == 'r') {
					sb.append('\r');
					nextCharacter();
				}
				else if (sc == 'n') {
					sb.append('\n');
					nextCharacter();
				}
				else {
					sb.append(escapeCharacter);
					sb.append((char)sc);
				}
			}
			else {
				sb.append((char)c);
				nextCharacter();
			}
		}
		nextCharacter();
		setValue(sb.toString());
		return true;
	}
}
