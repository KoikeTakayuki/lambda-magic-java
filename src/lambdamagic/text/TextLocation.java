package lambdamagic.text;

import lambdamagic.NullArgumentException;

public class TextLocation {

	public static final TextLocation NULL = new TextLocation("<Unknown Location>", TextPosition.NULL, TextPosition.NULL);
	
	private String sourceName;
	private TextPosition startPosition;
	private TextPosition endPosition;
	
	public TextLocation(String sourceName, TextPosition startPosition, TextPosition endPosition) {
		if (sourceName == null) {
			throw new NullArgumentException("sourceName");
		}
		
		if (startPosition == null) {
			throw new NullArgumentException("startPosition");
		}
		
		if (endPosition == null) {
			throw new NullArgumentException("endPosition");
		}
		
		this.sourceName = sourceName;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}
	
	public String getSourceName() {
		return sourceName;
	}

	public TextPosition getStartPosition() {
		return startPosition;
	}

	public TextPosition getEndPosition() {
		return endPosition;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != TextLocation.class)) {
			return false;
		}
		
		TextLocation other = (TextLocation)obj;
		return sourceName.equals(other.sourceName) &&
			   startPosition.equals(other.startPosition) &&
			   endPosition.equals(other.endPosition);
	}
	
	@Override
	public String toString() {
		return startPosition.equals(endPosition)
			? "[" + sourceName + ", " + startPosition + "]"
			: "[" + sourceName + ", " + startPosition + ", " + endPosition + "]";
	}
	
}
