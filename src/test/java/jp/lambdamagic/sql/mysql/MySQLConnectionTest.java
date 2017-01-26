package jp.lambdamagic.sql.mysql;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.sql.mysql.MySQLConnection;

public class MySQLConnectionTest {

	@Test(expected=NullArgumentException.class)
	public void MySQLConnection_mustThrowNullArgumentExceptionWhenNullUrlIsGiven() throws Exception {
		try(MySQLConnection connection = new MySQLConnection(null, "userName", "password")) {}
	}
	
	@Test(expected=NullArgumentException.class)
	public void MySQLConnection_mustThrowNullArgumentExceptionWhenNullUserNameIsGiven() throws Exception {
		try(MySQLConnection connection = new MySQLConnection("url", null, "password")) {}
	}
	
	@Test(expected=NullArgumentException.class)
	public void MySQLConnection_mustThrowNullArgumentExceptionWhenNullPasswordIsGiven() throws Exception {
		try(MySQLConnection connection = new MySQLConnection("url", "userName", null)) {}
	}
	
	@Test(expected=NullPointerException.class)
	public void MySQLConnection_mustThrowNullPointerExceptionWhenInvalidConnectionIsGiven() throws Exception {
		try(MySQLConnection connection = new MySQLConnection("url", "userName", "password")) {}
	}

}
