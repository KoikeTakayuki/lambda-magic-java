package jp.lambdamagic.json.data;

import java.io.IOException;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.JSONDataVisitor;

public class JSONNumber implements JSONData {

  private Number value;

  public JSONNumber(Number value) {
    if (value == null) {
      throw new NullArgumentException("value");
    }
    
    this.value = value;
  }
  
  public Number getValue() {
    return value;
  }
  
  @Override
  public void accept(JSONDataVisitor visitor) throws IOException {
    visitor.visit(this);
  }
  
}
