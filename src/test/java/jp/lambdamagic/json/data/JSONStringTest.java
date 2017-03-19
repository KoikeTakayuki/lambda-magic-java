package jp.lambdamagic.json.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class JSONStringTest {

    @Test(expected=NullArgumentException.class)
    public void JSONString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() {
        new JSONString(null);
    }
    
    @Test
    public void getValue_returnGivenString() {
        JSONString jsonString = new JSONString("test");
        assertThat(jsonString.getValue(), is("test"));
    }

}
