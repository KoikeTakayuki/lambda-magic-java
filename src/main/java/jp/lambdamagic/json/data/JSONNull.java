package jp.lambdamagic.json.data;

import java.io.IOException;

import jp.lambdamagic.json.JSONDataVisitor;

public class JSONNull implements JSONData {
  
  @Override
  public void accept(JSONDataVisitor visitor) throws IOException {
    visitor.visit(this);
  }
  
}