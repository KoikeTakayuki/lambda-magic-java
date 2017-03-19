package jp.lambdamagic.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataWriter;
import jp.lambdamagic.text.Characters;
import jp.lambdamagic.text.Encodings;

public class CSVWriter implements DataWriter<List<String>> {
  
  private static final String VALUE_DELIMITER_ESCAPE_STRING = "\"\"";

  private Writer writer;
  
  public CSVWriter(Writer writer) {
    if (writer == null) {
      throw new NullArgumentException("writer");
    }
    
    this.writer = writer;
  }

  public CSVWriter(String filePath, String encoding) throws IOException {
    if (filePath == null) {
      throw new NullArgumentException("filePath");
    }
    
    if (encoding == null) {
      throw new NullArgumentException("encoding");
    }
    
    this.writer = new BufferedWriter(
            new OutputStreamWriter(
              new FileOutputStream(new File(filePath)), encoding));
  }

  public CSVWriter(String filePath) throws IOException {
    this(filePath, Encodings.UTF_8);
  }

  @Override
  public void write(List<String> data) {
    if (data == null) {
      throw new NullArgumentException("data");
    }
    
    try {
      writer.write(toCSVString(data));
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
  
  private String toCSVString(List<String> data) {
    StringBuffer sb = new StringBuffer();
    int length = data.size();
    int i = 0;

    for (String s : data) {
      ++i;

      if (s != null) {

        if (hasSpecialCharacter(s)) {
          sb.append(escape(s));
        } else {
          sb.append(s);
        }

      }

      if (length > i) {
        sb.append(CSVParser.VALUE_SEPARATOR_CHAR);
      }
    }

    sb.append(CSVParser.ROW_SEPARATOR_CHAR);
    return sb.toString();
  }
  
  private String escape(String string) {
    return CSVParser.VALUE_DELIMITER_CHAR
        + string.replaceAll(String.valueOf(CSVParser.VALUE_DELIMITER_CHAR), VALUE_DELIMITER_ESCAPE_STRING)
        + CSVParser.VALUE_DELIMITER_CHAR;
  }
  
  private boolean hasSpecialCharacter(String s) {
    return s.indexOf(CSVParser.VALUE_DELIMITER_CHAR) != -1 ||
        s.indexOf(CSVParser.VALUE_SEPARATOR_CHAR) != -1 ||
        s.indexOf(CSVParser.ROW_SEPARATOR_CHAR) != -1 ||
        s.indexOf(Characters.CARRIAGE_RETURN) != -1;
  }
  
}
