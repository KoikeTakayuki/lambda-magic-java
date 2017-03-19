package jp.lambdamagic.web.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class HttpResponseTest {
    
    @SuppressWarnings("resource")
    @Test
    public void HttpResponse_acceptNullStatusMessage() {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        new HttpResponse(200, null, headerFields, in);
    }

    @SuppressWarnings("resource")
    @Test(expected=NullArgumentException.class)
    public void HttpResponse_mustThrowNullArgumentExceptionWhenNUllHeaderFieldsIsGiven() {
        InputStream in = new ByteArrayInputStream("test".getBytes());
        new HttpResponse(200, "test", null, in);
    }
    
    @SuppressWarnings("resource")
    @Test(expected=NullArgumentException.class)
    public void HttpResponse_mustThrowNullArgumentExceptionWhenNUllInputStreamIsGiven() {
        Map<String, String> headerFields = new HashMap<>();
        new HttpResponse(200, "test", headerFields, null);
    }
    
    @Test
    public void getStatusCode_returnGivenStatusCode() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            assertThat(response.getStatusCode(), is(200));    
        }
    }
    
    @Test
    public void getStatusMessage_returnGivenStatusMessage() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            assertThat(response.getStatusMessage(), is("test"));    
        }
    }
    
    @Test
    public void getHeaderFields_returnGivenHeaderFields() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            assertThat(response.getHeaderFields(), is(headerFields));    
        }
    }
    
    @Test
    public void getInputStream_returnGivenInputStream() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            assertThat(response.getInputStream(), is(in));    
        }
    }
    
    @Test(expected=NullArgumentException.class)
    public void getText_mustThrowNullArgumentExceptionWhenNullEncodingIsGiven() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            response.getText(null);    
        }
    }
    
    @Test(expected=UnsupportedEncodingException.class)
    public void getText_mustThrowUnsupportedEncodingExceptionWhenUnsupportedEncodingIsGiven() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            response.getText("unknownEncoding");    
        }
    }
    
    @Test
    public void getText_returnTextFromGivenInputStream() throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        InputStream in = new ByteArrayInputStream("test".getBytes());
        
        try(HttpResponse response = new HttpResponse(200, "test", headerFields, in)) {
            assertThat(response.getText(), is("test"));    
        }
    }

}
