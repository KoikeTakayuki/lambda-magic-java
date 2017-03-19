package jp.lambdamagic.web.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class HttpRequestTest {

  @Test(expected=NullArgumentException.class)
  public void HttpRequest_mustThrowNullArgumentExceptionWhenNullMethodIsGiven() {
    Map<String, String> headerFields = new HashMap<>();
    new HttpRequest(null, "test", headerFields);
  }
  
  @Test(expected=NullArgumentException.class)
  public void HttpRequest_mustThrowNullArgumentExceptionWhenNullUrlStringIsGiven() {
    Map<String, String> headerFields = new HashMap<>();
    new HttpRequest(HttpMethod.GET, null, headerFields);
  }
  
  @Test(expected=NullArgumentException.class)
  public void HttpRequest_mustThrowNullArgumentExceptionWhenNullHeaderFieldsIsGiven() {
    new HttpRequest(HttpMethod.GET, "test", null);
  }
  
  
  @Test
  public void HttpRequest_acceptNullData() {
    Map<String, String> headerFields = new HashMap<>();
    new HttpRequest(HttpMethod.GET, "test", headerFields, (byte[])null);
  }
  
  @Test
  public void getMethod_returnGivenMethod() {
    Map<String, String> headerFields = new HashMap<>();
    HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "test", headerFields);
    
    assertThat(httpRequest.getMethod(), is(HttpMethod.GET));
  }
  
  @Test
  public void getUrlString_returnGivenUrlString() {
    Map<String, String> headerFields = new HashMap<>();
    HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "test", headerFields);
    
    assertThat(httpRequest.getUrlString(), is("test"));
  }
  
  @Test
  public void getHeaderFields_returnGivenHeaderFields() {
    Map<String, String> headerFields = new HashMap<>();
    HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "test", headerFields);
    
    assertThat(httpRequest.getHeaderFields(), is(headerFields));
  }

}
