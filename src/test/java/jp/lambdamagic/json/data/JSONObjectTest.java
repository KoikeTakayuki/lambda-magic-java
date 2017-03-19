package jp.lambdamagic.json.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class JSONObjectTest {
  
  @Test(expected=NullArgumentException.class)
  public void get_throwNullArgumentExceptionWhenNullPropertyNameIsGiven() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.get(null);
  }

  @Test
  public void get_returnEmptyIfPropertyValueNotExists() {
    JSONObject jsonObject = new JSONObject();
    assertThat(jsonObject.get("test").isPresent(), is(false));
  }
  
  @Test
  public void set_setKeyValueToJSONObject() {
    JSONObject jsonObject = new JSONObject();
    
    assertThat(jsonObject.get("test").isPresent(), is(false));
    
    jsonObject.set("test", new JSONBoolean(true));
    
    assertThat(jsonObject.get("test").isPresent(), is(true));
    assertThat(jsonObject.get("test").get(), is(instanceOf(JSONBoolean.class)));
    assertThat(((JSONBoolean)jsonObject.get("test").get()).getValue(), is(true));
  }
  
  @Test
  public void propertyNames_returnPropertyNamesOfJSONObject() {
    JSONObject jsonObject = new JSONObject();
    
    jsonObject.set("test", new JSONBoolean(true));
    jsonObject.set("test2", new JSONBoolean(false));
    
    assertThat(jsonObject.propertyNames(), hasItem("test"));
    assertThat(jsonObject.propertyNames(), hasItem("test2"));
  }

  @Test
  public void entrySet_returnEntrySetOfJSONObject() {
    JSONObject jsonObject = new JSONObject();
    
    jsonObject.set("test", new JSONBoolean(true));
    jsonObject.set("test2", new JSONBoolean(false));
    
    Set<Entry<String, JSONData>> entrySet = jsonObject.entrySet();
    Iterator<Entry<String, JSONData>> it = entrySet.iterator();
    
    assertThat(it.hasNext(), is(true));
    Entry<String, JSONData> entry = it.next();
    assertThat(entry.getKey(), is("test"));
    assertThat(entry.getValue(), is(instanceOf(JSONBoolean.class)));
    assertThat(((JSONBoolean)entry.getValue()).getValue(), is(true));
    
    
    assertThat(it.hasNext(), is(true));
    entry = it.next();
    assertThat(entry.getKey(), is("test2"));
    assertThat(entry.getValue(), is(instanceOf(JSONBoolean.class)));
    assertThat(((JSONBoolean)entry.getValue()).getValue(), is(false));
  }

}
