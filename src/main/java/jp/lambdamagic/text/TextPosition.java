package jp.lambdamagic.text;

import jp.lambdamagic.InvalidArgumentException;

public final class TextPosition {

    private int lineNumber;
    private int columnNumber;
    
    public static TextPosition initialize() {
        return new TextPosition(1, 0);
    }
    
    public TextPosition(int lineNumber, int columnNumber) {
        if (lineNumber < 1) {
            throw new InvalidArgumentException("lineNumber", "lineNumber < 1");
        }
        
        if (columnNumber < 0) {
            throw new InvalidArgumentException("columnNumber", "columnNumber < 0");
        }
        
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
        return (lineNumber == other.lineNumber) && (columnNumber == other.columnNumber);
    }

    @Override
    public String toString() {
        return "(" + lineNumber + ", " + columnNumber + ")";
    }
}
