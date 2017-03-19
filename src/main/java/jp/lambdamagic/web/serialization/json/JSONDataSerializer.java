package jp.lambdamagic.web.serialization.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import jp.lambdamagic.json.JSONParser;
import jp.lambdamagic.json.JSONWriter;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.text.Encodings;
import jp.lambdamagic.web.MimeTypes;
import jp.lambdamagic.web.serialization.DataSerializer;

public class JSONDataSerializer implements DataSerializer<JSONData> {  

  @Override
  public String getMimeType() {
    return MimeTypes.APPLICATION_JAVASCRIPT;
  }

  @Override
  public String getEncoding() {
    return Encodings.UTF_8;
  }

  @Override
  public JSONData deserialize(Reader reader) throws IOException {
    return getJSONParser(reader).parse();
  }

  @Override
  public void serialize(Writer writer, JSONData json) throws IOException {
    getJSONWriter(writer).write(json);
  }
  
  private JSONWriter getJSONWriter(Writer writer) throws IOException {
    return new JSONWriter(writer);
  }
  
  private JSONParser getJSONParser(Reader reader) throws IOException {
    return new JSONParser(reader);
  }

}
