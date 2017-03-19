package jp.lambdamagic.web.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HttpCookieTest {

    @Test
    public void parse_parseCookieString() {
        HttpCookie cookie = HttpCookie.parse("CUSTOMER=WILE_E_COYOTE; PART_NUMBER=ROCKET_LAUNCHER_0001");
        
        assertThat(cookie.get("CUSTOMER").isPresent(), is(true));
        assertThat(cookie.get("CUSTOMER").get(), is("WILE_E_COYOTE"));
        
        assertThat(cookie.get("PART_NUMBER").isPresent(), is(true));
        assertThat(cookie.get("PART_NUMBER").get(), is("ROCKET_LAUNCHER_0001"));
        
        assertThat(cookie.get("test").isPresent(), is(false));
    }

}
