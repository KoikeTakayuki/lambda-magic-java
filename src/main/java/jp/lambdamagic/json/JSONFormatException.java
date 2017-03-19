package jp.lambdamagic.json;

import jp.lambdamagic.io.DataFormatException;
import jp.lambdamagic.text.TextPosition;

public class JSONFormatException extends DataFormatException {

    private static final long serialVersionUID = -2741307209833505759L;

    public JSONFormatException(String message) {
        super(message);
    }
    
    public JSONFormatException(String message, TextPosition textPosition) {
        super(message, textPosition);
    }
    
}
