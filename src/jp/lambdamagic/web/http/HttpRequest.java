package jp.lambdamagic.web.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.IOOperations;

public class HttpRequest {
		
	private HttpMethod method;
	private String urlString;
	private Map<String, String> headerFields;
	private byte[] data;
	
	public HttpRequest(HttpMethod method, String urlString, Map<String, String> headerFields, byte[] data) {
		if (method == null) {
			throw new NullArgumentException("method");
		}
		
		if (urlString == null) {
			throw new NullArgumentException("urlSring");
		}
		
		if (headerFields == null) {
			throw new NullArgumentException("headerFields");
		}
		
		this.method = method;
		this.urlString = urlString;
		this.headerFields = headerFields;
		this.data = data;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUrlString() {
		return urlString;
	};

	public Map<String, String> getHeaderFields() {
		return headerFields;
	};

	public HttpRequest(HttpMethod method, String urlString, Map<String, String> headerFields, String data) {
		this(method, urlString, headerFields, (data == null ? null : data.getBytes()));
	}

	public HttpRequest(HttpMethod method, String urlString, Map<String, String> headerFields) {
		this(method, urlString, headerFields, (byte[])null);
	}

	protected void writeData(OutputStream outputStream) throws IOException {
		if (data == null) {
			throw new IllegalStateException("data == null");
		}
	
		IOOperations.writeAllBytes(outputStream, data);
	}

}
