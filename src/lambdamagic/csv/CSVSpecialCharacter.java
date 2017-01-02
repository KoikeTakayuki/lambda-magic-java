package lambdamagic.csv;

public final class CSVSpecialCharacter {
	public static final char ROW_SEPARATOR_CHAR = '\n';
	public static final char VALUE_SEPARATOR_CHAR = ',';
	public static final char VALUE_DELIMITER_CHAR = '"';
	public static final char VALUE_ESCAPE_CHAR = '\\';
	public static final char[] DEFAULT_VALUE_SEPARATORS = new char[] { VALUE_SEPARATOR_CHAR, ROW_SEPARATOR_CHAR };
}
