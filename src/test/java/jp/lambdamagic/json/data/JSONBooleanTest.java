package jp.lambdamagic.json.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class JSONBooleanTest {

    @Test
    public void getValue_returnGivenBoolean() {
        JSONBoolean jsonTrue = new JSONBoolean(true);
        assertThat(jsonTrue.getValue(), is(true));
        
        JSONBoolean jsonFalse = new JSONBoolean(false);
        assertThat(jsonFalse.getValue(), is(false));
    }

}
