package jp.lambdamagic.web.serialization;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface DataSerializer<T> {
  String getMimeType();
  String getEncoding();
  T deserialize(Reader reader) throws IOException;
  void serialize(Writer writer, T data) throws IOException;
}
