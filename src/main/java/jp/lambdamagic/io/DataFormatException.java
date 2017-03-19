package jp.lambdamagic.io;

import java.io.IOException;

import jp.lambdamagic.text.TextPosition;

public class DataFormatException extends IOException {

    private static final long serialVersionUID = -7574631793109346108L;

    public DataFormatException(String message) {
        super(message);
    }
    
    public DataFormatException(String message, TextPosition textPosition) {
        this(message + " at " + textPosition);
    }
    
}
