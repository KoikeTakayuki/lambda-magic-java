package lambdamagic.web.http.service.rest;

import lambdamagic.NullArgumentException;

public class HttpRestParameter {

	private String name;
	private Class<?> type;
	private boolean required;
	private Object defaultValue;
	
	private HttpRestParameter(String name, Class<?> type, boolean required, Object defaultValue) {
		if (name == null) {
			throw new NullArgumentException("name");
		}
		
		if (type == null) {
			throw new NullArgumentException("type");
		}
		
		this.name = name;
		this.type = type;
		this.required = required;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public HttpRestParameter(String name, Class<?> type, Object defaultValue) {
		this(name, type, false, defaultValue);
	}
	
	public HttpRestParameter(String name, Class<?> type) {
		this(name, type, true, null);
	}

}
