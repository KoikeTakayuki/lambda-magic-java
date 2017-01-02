package lambdamagic.text;


public final class TextPosition {

	public static final TextPosition NULL = new TextPosition(0, 0);
	
	private int lineNumber;
	private int columnNumber;
	
	public static TextPosition initialize() {
		return new TextPosition(1, 0);
	}
	
	public TextPosition(int lineNumber, int columnNumber) {
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public int getColumnNumber() {
		return columnNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != TextPosition.class)) {
			return false;
		}
		
		TextPosition other = (TextPosition)obj;
		return (lineNumber == other.lineNumber) &&
			   (columnNumber == other.columnNumber);
	}

	@Override
	public String toString() {
		return "(" + lineNumber + ", " + columnNumber + ")";
	}
}
