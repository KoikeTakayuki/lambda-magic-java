package lambdamagic.web.http.service.rest;

import lambdamagic.NullArgumentException;
import lambdamagic.web.http.HttpMethod;

public class HttpRestOperation {

	private String path;
	private HttpMethod method;
	private String name;
	private HttpRestParameter[] parameters;

	public HttpMethod getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
	
	public HttpRestParameter[] getParameters() {
		return parameters;
	}
	
	public HttpRestOperation(HttpMethod method, String path, String name, HttpRestParameter... parameters) {
		if (method == null)
			throw new NullArgumentException("method");
		
		if (path == null)
			throw new NullArgumentException("path");
		
		if (name == null)
			throw new NullArgumentException("name");
		
		this.method = method;
		this.path = path;
		this.name = name;
		this.parameters = parameters;
	}
}
