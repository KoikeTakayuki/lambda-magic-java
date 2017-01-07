package jp.lambdamagic.web.http;

import jp.lambdamagic.data.functional.Either;
import jp.lambdamagic.settings.PropertyStringParser;

public class HttpCookieParser extends PropertyStringParser<HttpCookie> {

	public HttpCookieParser(String string) {
		super(string);
	}

	@Override
	public Either<HttpCookie, HttpCookieFormatException> parse() {
		return parsePropertyStringToMap()
				.applyToLeft(map -> new HttpCookie(map))
				.applyToRight(e -> (HttpCookieFormatException)e);
	}

}
