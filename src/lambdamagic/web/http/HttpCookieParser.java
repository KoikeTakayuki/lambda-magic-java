package lambdamagic.web.http;

import lambdamagic.data.functional.Either;
import lambdamagic.settings.PropertyStringParser;

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
