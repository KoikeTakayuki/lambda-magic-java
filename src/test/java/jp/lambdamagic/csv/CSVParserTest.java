package jp.lambdamagic.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.EndOfStreamException;

public class CSVParserTest {
  
  private void assertEndOfStream(CSVParser parser) {
    try {
      parser.parse();
    } catch (EndOfStreamException ok) {
      
    } catch (Exception e) {
      fail();
    }
  }

  @SuppressWarnings("resource")
  @Test(expected=NullArgumentException.class)
  public void CSVParser_mustThrowExceptionWhenNullReaderIsGiven() throws IOException {
    new CSVParser(null);
  }
  
  @Test(expected=NullArgumentException.class)
  public void fromString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() throws IOException {
    CSVParser.fromString(null);
  }
  
  @Test
  public void parse_mustThrowCSVFormatExceptionWhenInvalidCSVStringIsGiven() {
    try {
      CSVParser.fromString("\"test").parse();
    } catch (CSVFormatException ok) {
      assertThat(ok.getMessage(), is("Unclosed string at (1, 1)"));
    } catch (Exception e) {
      fail();
    }
  }
  
  @Test
  public void parse_mustThrowCSVFormatExceptionWhenInvalidCSVFormatIsGiven() {
    try {
      CSVParser.fromString("\"test\"test").parse();
    } catch (CSVFormatException ok) {
      assertThat(ok.getMessage(), is("CSV value separator ',' is expected, but actual 't' is given at (1, 7)"));
    } catch (Exception e) {
      fail();
    }
  }
  
  @Test
  public void parse_parseCSVValue() throws IOException {
    CSVParser parser = CSVParser.fromString("test");
    List<String> parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("test")));
    assertEndOfStream(parser);
  }
  
  @Test(expected=EndOfStreamException.class)
  public void parse_mustThrowEndOfStreamExceptionWhenEmptyStringIsGiven() throws IOException {
    CSVParser.fromString("").parse();
  }
  
  @Test
  public void parse_parseCSVString() throws IOException {
    CSVParser parser = CSVParser.fromString("\"test\"");
    List<String> parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("test")));
    assertEndOfStream(parser);
  }
  
  @Test
  public void parse_returnEmptyListWhenParsingWhitespace() throws IOException {
    CSVParser parser = CSVParser.fromString("   ");
    List<String> parseResult = parser.parse();
    assertThat(parseResult.size(), is(0));
    assertEndOfStream(parser);
  }
  
  @Test
  public void parse_ignoreWhitespaces() throws IOException {
    CSVParser parser = CSVParser.fromString("     test   ");
    List<String>parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("test")));
    assertEndOfStream(parser);
  }
  
  @Test
  public void parse_parseCSVStringIncludingSpecialCharacter() throws IOException {
    CSVParser parser = CSVParser.fromString("\"\"\"test\"\"\", \"\n\", \"\r\"  \n");
    List<String> parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("\"test\"", "\n", "\r")));
    assertEndOfStream(parser);
  }
  
  @Test
  public void parse_parseEmptyValue() throws IOException {
    CSVParser parser = CSVParser.fromString(",\n");
    List<String> parseResult = parser.parse();
    assertThat(parseResult.size(), is(2));
    assertThat(parseResult.get(0), is(""));
    assertThat(parseResult.get(1), is(""));
    assertEndOfStream(parser);
  }
  
  @Test
  public void parse_parseCSVRow() throws IOException {
    CSVParser parser = CSVParser.fromString("   test , 1, true,   \"\"\"ok\"\n1e+10, test2  \n");
    List<String> parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("test", "1", "true", "\"ok")));
    
    parseResult = parser.parse();
    assertThat(parseResult, is(Arrays.asList("1e+10", "test2")));
    
    assertEndOfStream(parser);
  }
  
}
