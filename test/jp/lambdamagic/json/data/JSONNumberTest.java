package jp.lambdamagic.json.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class JSONNumberTest {

	@Test(expected=NullArgumentException.class)
	public void JSONNumber_mustThrowNullArgumentExceptionWhenNullNumberIsGiven() {
		new JSONNumber(null);
	}
	
	@Test
	public void getValue_returnGivenNumber() {
		JSONNumber jsonNumber = new JSONNumber(100);
		assertThat(jsonNumber.getValue(), is(100));
	}

}
