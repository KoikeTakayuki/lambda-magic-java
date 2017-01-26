package jp.lambdamagic.web.http.service.rest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.web.http.HttpMethod;
import jp.lambdamagic.web.http.service.rest.HttpRestOperation;
import jp.lambdamagic.web.http.service.rest.HttpRestParameter;

public class HttpRestOperationTest {

	@Test(expected=NullArgumentException.class)
	public void HttpRestOperation_mustThrowNullArgumentExceptionWhenNullMethodIsGiven() {
		new HttpRestOperation(null, "path", "name");
	}
	
	@Test(expected=NullArgumentException.class)
	public void HttpRestOperation_mustThrowNullArgumentExceptionWhenNullPathIsGiven() {
		new HttpRestOperation(HttpMethod.POST, null, "name");
	}
	
	@Test(expected=NullArgumentException.class)
	public void HttpRestOperation_mustThrowNullArgumentExceptionWhenNullNameIsGiven() {
		new HttpRestOperation(HttpMethod.POST, "path", null);
	}

	@Test
	public void getMethod_mustReturnGivenMethod() {
		HttpRestOperation operation = new HttpRestOperation(HttpMethod.POST, "path", "name");
		assertEquals(HttpMethod.POST, operation.getMethod());
	}
	
	@Test
	public void getPath_mustReturnGivenPath() {
		HttpRestOperation operation = new HttpRestOperation(HttpMethod.POST, "path", "name");
		assertEquals("path", operation.getPath());
	}
	
	@Test
	public void getPath_mustReturnGivenName() {
		HttpRestOperation operation = new HttpRestOperation(HttpMethod.POST, "path", "name");
		assertEquals("name", operation.getName());
	}
	
	@Test
	public void getParameters_mustReturnGivenHttpRestParameters() {
		HttpRestParameter parameter = new HttpRestParameter("name", Object.class);
		HttpRestOperation operation = new HttpRestOperation(HttpMethod.POST, "path", "name", parameter);
		
		assertArrayEquals(new HttpRestParameter[]{ parameter }, operation.getParameters());
	}
	
	@Test
	public void getParameters_mustReturnEmptyArrayWhenParameterIsNotGiven() {
		HttpRestOperation operation = new HttpRestOperation(HttpMethod.POST, "path", "name");
		assertArrayEquals(new HttpRestParameter[]{}, operation.getParameters());
	}

}
