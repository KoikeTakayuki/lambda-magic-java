package lambdamagic.json;

import java.util.zip.DataFormatException;

import lambdamagic.text.TextPosition;

public class JSONFormatException extends DataFormatException {

	private static final long serialVersionUID = -2741307209833505759L;

	public JSONFormatException(TextPosition position) {
		super("Invalid JSON format at " + position);
	}
}
