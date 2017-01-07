package jp.lambdamagic.web.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.functional.Either;


public final class HttpClient {

	private HttpCookie lastCookie;
	private boolean forwardCookies;
	
	public HttpClient(boolean forwardCookies) {
		this.forwardCookies = forwardCookies;
	}
	
	public HttpClient() {
		this(true);
	}
	
	public HttpCookie getLastCookie() {
		return lastCookie;
	}

	public HttpResponse get(String urlString) throws MalformedURLException, IOException {
		return get(urlString, Collections.<String, String>emptyMap());
	}

	public HttpResponse get(String urlString, Map<String, String> requestHeaderFields) throws MalformedURLException, IOException {
		return sendRequest(createRequest(HttpMethod.GET, urlString, requestHeaderFields, null));
	}

	public HttpResponse post(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(createRequest(HttpMethod.POST, urlString, requestHeaderFields, data));
	}

	public HttpResponse put(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(createRequest(HttpMethod.PUT, urlString, requestHeaderFields, data));
	}

	public HttpResponse delete(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(createRequest(HttpMethod.DELETE, urlString, requestHeaderFields, data));
	}
	
	private HttpRequest createRequest(HttpMethod method, String urlString, Map<String, String> requestHeaderFields, String data) {
		return new HttpRequest(method, urlString, requestHeaderFields, data);
	}
	
	private HttpResponse createResponse(int statusCode, String statusMessage, Map<String, String> headerFields, InputStream inputStream) {
		return new HttpResponse(statusCode, statusMessage, headerFields, inputStream);
	}
	
	private HttpResponse sendRequest(HttpRequest request) throws MalformedURLException, IOException {
		if (request == null) {
			throw new NullArgumentException("request");
		}
		
		HttpURLConnection connection = (HttpURLConnection)new URL(request.getUrlString()).openConnection();
		
		for (Entry<String, String> f : request.getHeaderFields().entrySet()) {
			connection.setRequestProperty(f.getKey(), f.getValue());
		}
		
		if (forwardCookies && (lastCookie != null)) {
			connection.setRequestProperty(HttpHeaderField.COOKIE, lastCookie.toString());
		}
		
		if (request.getMethod() != HttpMethod.GET) {
			connection.setDoOutput(true);
			
			try (OutputStream outputStream = connection.getOutputStream()) {
				request.writeData(outputStream);
			}
		}

		String cookieString = connection.getHeaderField(HttpHeaderField.SET_COOKIE);
		
		if (cookieString != null) {
			HttpCookieParser parser = new HttpCookieParser(cookieString);
			Either<HttpCookie, HttpCookieFormatException> parseResult = parser.parse();
			
			if (parseResult.isLeft()) {
				lastCookie = parseResult.getLeft();
			}
		}

		InputStream errorStream = connection.getErrorStream();
		Map<String, String> responseHeaderFields = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < connection.getHeaderFields().size(); ++i) {
			String key = connection.getHeaderFieldKey(i);
			
			if (key == null) {
				continue;
			}
			
			String value = connection.getHeaderField(i);
			responseHeaderFields.put(key, value);
		}

		return createResponse(connection.getResponseCode(), connection.getResponseMessage(),
					Collections.unmodifiableMap(responseHeaderFields),
					(errorStream != null) ? errorStream : connection.getInputStream());
		
	}
	
}