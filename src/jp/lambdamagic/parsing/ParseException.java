package jp.lambdamagic.parsing;

import jp.lambdamagic.text.TextPosition;

public class ParseException extends Exception {

	private static final long serialVersionUID = 6107458171037508619L;

	public ParseException(String message, TextPosition position) {
		super("Failed to parse at " + position + " " + message);
	}
	
}
