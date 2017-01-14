package jp.lambdamagic.web.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import jp.lambdamagic.io.IOOperations;
import jp.lambdamagic.text.Encodings;

public final class HttpClient {
	
	private static final String FILE_UPLOAD_BOUNDARY = "fileUploadBoundary";

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

	public HttpResponse get(String urlString, Map<String, String> requestHeaderFields) throws IOException {
		return sendRequest(new HttpRequest(HttpMethod.GET, urlString, requestHeaderFields));
	}

	public HttpResponse post(String urlString, Map<String, String> requestHeaderFields, String data) throws IOException {
		return sendRequest(new HttpRequest(HttpMethod.POST, urlString, requestHeaderFields, data));
	}

	public HttpResponse put(String urlString, Map<String, String> requestHeaderFields, String data) throws IOException {
		return sendRequest(new HttpRequest(HttpMethod.PUT, urlString, requestHeaderFields, data));
	}

	public HttpResponse delete(String urlString, Map<String, String> requestHeaderFields, String data) throws IOException {
		return sendRequest(new HttpRequest(HttpMethod.DELETE, urlString, requestHeaderFields, data));
	}
	
	public HttpResponse uploadFile(String urlString, String filePath) throws MalformedURLException, IOException {
		if (urlString == null) {
			throw new NullArgumentException("urlString");
		}
		
		if (filePath == null) {
			throw new NullArgumentException("filePath");
		}
	
		final byte[] REQUEST_START = ("--" + FILE_UPLOAD_BOUNDARY + "\r\n" +
				HttpHeaderField.CONTENT_DISPOSITION + ": " + HttpHeaderFieldValue.CONTENT_DISPOSITION_FORM_DATA +
				"; name=\"uploadedfile\";filename=\"" + filePath + "\"\r\n\r\n").getBytes(Encodings.UTF_8);
		
		final byte[] REQUEST_END = ("\r\n--" + FILE_UPLOAD_BOUNDARY + "--").getBytes(Encodings.UTF_8);
		
		final File file = new File(filePath);
		long contentLength = REQUEST_START.length + file.length() + REQUEST_END.length;
		
		Map<String, String> headerFields = new LinkedHashMap<String, String>();
		
		headerFields.put(HttpHeaderField.CONTENT_TYPE, HttpHeaderFieldValue.CONTENT_TYPE_MULTIPART_FORM_DATA + "; boundary=" + FILE_UPLOAD_BOUNDARY);
		headerFields.put(HttpHeaderField.CONTENT_LENGTH, Long.toString(contentLength));
		
		return sendRequest(new HttpRequest(HttpMethod.POST, urlString, headerFields) {
			
			@Override
			protected void writeData(OutputStream outputStream) throws IOException {
				outputStream.write(REQUEST_START);
				try (InputStream inputStream = new FileInputStream(file)) {
					IOOperations.copy(inputStream, outputStream, -1);
				}
				outputStream.write(REQUEST_END);
			}
		});
	}

	public void downloadFile(String urlString, String filePath) throws MalformedURLException, IOException {
		HttpRequest request = new HttpRequest(HttpMethod.GET, urlString, Collections.<String, String>emptyMap());
		
		try (HttpResponse response = sendRequest(request);
			 OutputStream ouputStream = new FileOutputStream(filePath)) {
			IOOperations.copy(response.getInputStream(), ouputStream, -1);
		}
	}
	
	private HttpResponse sendRequest(HttpRequest request) throws IOException {		
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