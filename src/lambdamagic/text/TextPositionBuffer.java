package lambdamagic.text;

import java.util.ArrayList;
import java.util.List;

import lambdamagic.NullArgumentException;

public class TextPositionBuffer {

	private int lineNumber;
	private int columnNumber;

	private StringBuffer stringBuffer;
	
	public String getInputString() {
		return stringBuffer.toString();
	}

	public TextPositionBuffer(TextPosition position) {
		if (position == null)
			throw new NullArgumentException("position");
		
		this.lineNumber = position.getLineNumber();
		this.columnNumber = position.getColumnNumber();
		this.stringBuffer = new StringBuffer();
	}

	public TextPosition toTextPosition() {
		return new TextPosition(lineNumber, columnNumber);
	}

	public void update(char c) {
		stringBuffer.append(c);
		
		if (Characters.isNewLine(c)) {
			++lineNumber;
			columnNumber = 1;
		} else
			++columnNumber;
	}
}
