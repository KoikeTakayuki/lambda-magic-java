package lambdamagic.web.http.service.rest;

public class HttpRestServiceException extends Exception {
	
	private static final long serialVersionUID = 3755235187278448907L;

	private String className;
	
	public String getClassName() {
		return className;
	}
	
	public HttpRestServiceException(String className, String message) {
		super(message);
		
		this.className = className;
	}
	
	public HttpRestServiceException(String message) {
		this(null, message);
	}
}
