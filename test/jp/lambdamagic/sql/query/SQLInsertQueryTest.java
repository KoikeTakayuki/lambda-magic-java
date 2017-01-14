package jp.lambdamagic.sql.query;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class SQLInsertQueryTest {

	@Test(expected=NullArgumentException.class)
	public void SQLInsertQuery_mustThrowNullArgumentExceptionWhenNullTableNameIsGiven() {
		Map<String, Object> values = new HashMap<>();
		new SQLInsertQuery(null, values);
	}
	
	@Test(expected=NullArgumentException.class)
	public void SQLInsertQuery_mustThrowNullArgumentExceptionWhenNullInsertValuesIsGiven() {
		new SQLInsertQuery("test", null);
	}
	
	@Test
	public void getTableName_returnGivenTableName() {
		Map<String, Object> values = new HashMap<>();
		SQLInsertQuery query = new SQLInsertQuery("test", values);
		
		assertThat(query.getTableName(), is("test"));
	}
	
	@Test
	public void getInsertValues_returnGivenInsertValues() {
		Map<String, Object> values = new HashMap<>();
		SQLInsertQuery query = new SQLInsertQuery("test", values);
		
		assertThat(query.getInsertValues(), is(values));
	}

}
