package jp.lambdamagic.web.http.service.rest;

import java.util.Map;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.web.http.HttpMethod;


public class HttpRestOperationInvocationContext {

  private HttpMethod method;
  private String name;
  private Map<String, Object> arguments;
  private boolean compressed;
  private boolean longPolling;
  
  public HttpRestOperationInvocationContext(HttpMethod method, String name, Map<String, Object> arguments, boolean compressed, boolean longPolling) {
    if (method == null) {
      throw new NullArgumentException("method");
    }
    
    if (name == null) {
      throw new NullArgumentException("name");
    }
      
    if (arguments == null) {
      throw new NullArgumentException("arguments");
    }
    
    this.method = method;
    this.name = name;
    this.arguments = arguments;
    this.compressed = compressed;
    this.longPolling = longPolling;
  }
  
  public HttpMethod getMethod() {
    return method;
  }
  
  public String getName() {
    return name;
  }
  
  public Map<String, Object> getArguments() {
    return arguments;
  }
  
  public boolean isCompressed() {
    return compressed;
  }
  
  public boolean isLongPolling() {
    return longPolling;
  }

}
