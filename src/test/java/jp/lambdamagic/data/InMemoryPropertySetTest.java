package jp.lambdamagic.data;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class InMemoryPropertySetTest {

    
    @Test(expected=NullArgumentException.class)
    public void get_mustThrowNullArgumentExceptionWhenNullPropertyNameIsGiven() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        propertySet.get(null);
    }
    
    @Test
    public void get_returnEmptyIfPropertyValueNotExists() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        Optional<String> test = propertySet.get("test");
        assertThat(test.isPresent(), is(false));
    }
    
    @Test(expected=NullArgumentException.class)
    public void set_mustThrowNullArgumentExceptionWhenNullPropertyNameIsGiven() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        propertySet.set(null, "value");
    }
    
    @Test(expected=NullArgumentException.class)
    public void set_mustThrowNullArgumentExceptionWhenNullValueIsGiven() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        propertySet.set("key", null);
    }
    
    @Test
    public void set_setKeyValueToSettings() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        
        Optional<String> maybeValue = propertySet.get("key");
        assertThat(maybeValue.isPresent(), is(false));
        
        propertySet.set("key", "value");
        
        maybeValue = propertySet.get("key");
        assertThat(maybeValue.isPresent(), is(true));
        assertThat(maybeValue.get(), is("value"));
    }
    
    @Test
    public void propertyNames_returnPropertyNamesOfPropertySet() throws IOException {
        PropertySet<String> propertySet = new InMemoryPropertySet<String>();
        propertySet.set("key1", "value1");
        propertySet.set("key2", "value2");
        assertThat(propertySet.propertyNames(), hasItem("key1"));
        assertThat(propertySet.propertyNames(), hasItem("key2"));
        assertThat(propertySet.propertyNames(), not(hasItem("key3")));
    }

}
