package jp.lambdamagic.json.data;

import java.io.IOException;
import java.util.ArrayList;

import jp.lambdamagic.json.JSONDataVisitor;

public class JSONArray extends ArrayList<JSONData> implements JSONData {
  
  private static final long serialVersionUID = 2594587991927832723L;

  @Override
  public void accept(JSONDataVisitor visitor) throws IOException {
    visitor.visit(this);
  }
  
}
