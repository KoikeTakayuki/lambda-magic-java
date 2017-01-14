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
		return sendRequest(new HttpRequest(HttpMethod.GET, urlString, requestHeaderFields));
	}

	public HttpResponse post(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(new HttpRequest(HttpMethod.POST, urlString, requestHeaderFields, data));
	}

	public HttpResponse put(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(new HttpRequest(HttpMethod.PUT, urlString, requestHeaderFields, data));
	}

	public HttpResponse delete(String urlString, Map<String, String> requestHeaderFields, String data) throws MalformedURLException, IOException {
		return sendRequest(new HttpRequest(HttpMethod.DELETE, urlString, requestHeaderFields, data));
	}
	
	private HttpResponse sendRequest(HttpRequest request) throws MalformedURLException, IOException {		
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
			lastCookie = HttpCookie.parse(cookieString);
		}

		Map<String, String> responseHeaderFields = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < connection.getHeaderFields().size(); ++i) {
			String key = connection.getHeaderFieldKey(i);
			
			if (key == null) {
				continue;
			}
			
			String value = connection.getHeaderField(i);
			responseHeaderFields.put(key, value);
		}
		
		InputStream errorStream = connection.getErrorStream();
		InputStream resultStream = (errorStream != null) ? errorStream : connection.getInputStream();

		return new HttpResponse(connection.getResponseCode(), connection.getResponseMessage(),
					Collections.unmodifiableMap(responseHeaderFields), resultStream);
		
	}
	
}