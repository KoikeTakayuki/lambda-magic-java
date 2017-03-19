package jp.lambdamagic.web.http.service.rest;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONData;

public class HttpRestParameter {

  private String name;
  private Class<?> type;
  private boolean required;
  private JSONData defaultValue;
  
  private HttpRestParameter(String name, Class<?> type, boolean required, JSONData defaultValue) {
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
  
  public JSONData getDefaultValue() {
    return defaultValue;
  }
  
  public HttpRestParameter(String name, Class<?> type, JSONData defaultValue) {
    this(name, type, false, defaultValue);
  }
  
  public HttpRestParameter(String name, Class<?> type) {
    this(name, type, true, null);
  }

}
