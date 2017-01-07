package jp.lambdamagic.web.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.IOOperations;
import jp.lambdamagic.text.Encodings;


public final class HttpResponse implements Closeable {

	public static final String DEFAULT_ENCODING = Encodings.UTF_8;
	
	private int statusCode;
	private String statusMessage;
	private Map<String, String> headerFields;
	private InputStream inputStream;
	private String text;
	
	public HttpResponse(int statusCode, String statusMessage, Map<String, String> headerFields, InputStream inputStream) {
		if (inputStream == null) {
			throw new NullArgumentException("inputStream");
		}
		
		if (headerFields == null) {
			throw new NullArgumentException("headerFields");
		}
		
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.headerFields = headerFields;
		this.inputStream = inputStream;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public Map<String, String> getHeaderFields() {
		return headerFields;
	}
	
	
	public InputStream getInputStream() {
		if (text != null) {
			throw new IllegalStateException("input stream has already been read by \"getText\" before invoking \"getInputStream\"");
		}
		
		return inputStream;
	}

	
	public String getText(String encoding) {
		if (text == null) {
			try {
				text = IOOperations.readAllText(new InputStreamReader(inputStream, encoding));
			} catch (Exception e) {
				text = "";
			}
		}
		
		return text;
	}

	public String getText() {
		return getText(DEFAULT_ENCODING);
	}

	@Override
	public void close() throws IOException {
		getInputStream().close();
	}
	
}
