package lambdamagic.text;

public final class Characters {
	
	public static final int END_OF_STREAM_CODE = -1;
	public static final char SPACE = ' ';
	public static final char TAB = '\t';
	public static final char CARRIAGE_RETURN = '\r';
	public static final char NEW_LINE = '\n';
	
	public static boolean isNewLine(int c) {
		return c == NEW_LINE;
	}
	
	public static boolean isWhitespace(int c) {
		return c == SPACE || c == TAB || c == CARRIAGE_RETURN || c == NEW_LINE;
	}
	
	public static boolean isAlphabetic(int c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}
	
	public static boolean isDigit(int c) {
		return c >= '0' && c <= '9';
	}
	
	public static boolean isEndOfStream(int c) {
		return c == END_OF_STREAM_CODE;
	}
	
}
