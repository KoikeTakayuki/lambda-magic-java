package lambdamagic.csv;

import lambdamagic.text.Characters;

public final class CSVSpecialCharacter {
	
	public static final char ROW_SEPARATOR_CHAR = '\n';
	public static final char VALUE_SEPARATOR_CHAR = ',';
	public static final char VALUE_DELIMITER_CHAR = '"';
	public static final char VALUE_ESCAPE_CHAR = '\\';
	
	public static final char[] DEFAULT_VALUE_SEPARATORS = new char[] { VALUE_SEPARATOR_CHAR, ROW_SEPARATOR_CHAR };
	
	public static final String VALUE_DELIMITER_ESCAPE_STRING = "\"\"";

	public static boolean hasSpecialCharacter(String s) {
		return s.indexOf(VALUE_DELIMITER_CHAR) != -1 ||
				s.indexOf(VALUE_SEPARATOR_CHAR) != -1 ||
				s.indexOf(ROW_SEPARATOR_CHAR) != -1 ||
				s.indexOf(Characters.CARRIAGE_RETURN) != -1;
	}

	public static  String escape(String s) {
		return VALUE_DELIMITER_CHAR
				+ s.replace(String.valueOf(VALUE_DELIMITER_CHAR), VALUE_DELIMITER_ESCAPE_STRING)
				+ VALUE_DELIMITER_CHAR;
	}
}
