package jp.lambdamagic.web.http;

import jp.lambdamagic.settings.PropertyStringFormatException;

public class HttpCookieFormatException extends PropertyStringFormatException {

	private static final long serialVersionUID = 8274898216769608314L;
	
	public HttpCookieFormatException(String message) {
		super(message);
	}

}
