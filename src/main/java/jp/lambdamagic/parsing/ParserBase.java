package jp.lambdamagic.parsing;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.DataFormatException;
import jp.lambdamagic.text.Characters;
import jp.lambdamagic.text.TextPosition;

public abstract class ParserBase<T> implements Parser<T>, Closeable  {

  private int c;
  private int lineNumber;
  private int lineOffset;
  private Map<String, Object> specialMap;
  private int digitCount;
  protected Reader reader;
  
  protected int getCharacter() {
    return c;
  }

  protected TextPosition getPosition() {
    return new TextPosition(lineNumber, lineOffset);
  }
  
  protected boolean isEndOfStream() throws IOException {
    return Characters.isEndOfStream(getCharacter());
  }
  
  protected boolean isEndOfLine() throws IOException {
    return Characters.isNewLine(getCharacter());
  }
  
  protected boolean isWhitespace() throws IOException {
    return Characters.isWhitespace(getCharacter());
  }
  
  protected boolean isAlphabetic() throws IOException {
    return Characters.isAlphabetic(getCharacter());
  }
  
  protected boolean isDigit() throws IOException {
    return Characters.isDigit(getCharacter());
  }

  protected boolean isValidIdFirstCharacter() throws IOException {
    return isAlphabetic();
  }
  
  protected boolean isValidNumberFirstCharacter() throws IOException {
    return isDigit() || (getCharacter() == '-');
  }

  protected boolean isValidIdCharacter() throws IOException {
    return isAlphabetic() || isDigit() || getCharacter() == '_';
  }

  protected Map<String, Object> getSpecialMap() {
    if (specialMap == null) {
      specialMap = new HashMap<String, Object>();
    }
    
    return specialMap;
  }

  public ParserBase(Reader reader) throws IOException {
    if (reader == null) {
      throw new NullArgumentException("reader");
    }
    
    this.lineNumber = 1;
    this.reader = reader;
    nextCharacter();
  }

  public ParserBase(String string) throws IOException {
    if (string == null) {
      throw new NullArgumentException("string");
    }
    
    this.reader = new StringReader(string);
    nextCharacter();
  }

  protected int readCharacter() throws IOException {
    return reader.read();
  };
  
  protected int nextCharacter() throws IOException {
    int pc = c;
    c = readCharacter();
    
    if (Characters.isNewLine(c)) {
      ++lineNumber;
      lineOffset = 0;
    } else {
      ++lineOffset;
    }
    return pc;
  }
  
  protected void skipWhitespacesUntilNewline() throws IOException {
    while (isWhitespace() && !isEndOfLine()) {
      nextCharacter();
    }
  }
  
  protected void skipWhitespaces() throws IOException {
    while (isWhitespace()) {
      nextCharacter();
    }
  }
  
  protected String parseUntil(char[] delimeters) throws IOException {  
    StringBuffer sb = new StringBuffer();
      
    while (!isEndOfStream()) {
      int c = getCharacter();
      
      for (char d : delimeters) {
        if (c == d) {
          return sb.toString();
        }
      }

      sb.append((char)c);
      nextCharacter();
    }

    return sb.toString();
  }
  
  protected String parseId() throws IOException {      
    StringBuffer sb = new StringBuffer();

    if (!isValidIdFirstCharacter()) {
      throw new DataFormatException("Invalid first ID character '" + (char)getCharacter() + "'", getPosition());
    }
      
    sb.append((char)nextCharacter());
      
    while (isValidIdCharacter()) {
      sb.append((char)nextCharacter());
    }
      
    return sb.toString();
  }
  
  protected String parseSpecial() throws IOException {
    TextPosition positionBeforeParsing = getPosition();
    String key = parseId();

    if (!getSpecialMap().containsKey(key)) {
      throw new DataFormatException("ID: " + key + " isn't special", positionBeforeParsing);
    }
    
    return (String)getSpecialMap().get(key);
  }
  
  protected double parseNumber() throws IOException {  
    int integerPart = parseInteger();
      
    // has neither decimal part nor normalized notation, finish parsing number
    if (getCharacter() != '.' && getCharacter() != 'e' && getCharacter() != 'E') {
      return integerPart;
    }
      
    double decimalPart = 0;
    double result = integerPart;

    // parsing decimal part
    if (getCharacter() == '.') {
      nextCharacter();
      decimalPart = parseUnsignedInteger();
      result = integerPart + (decimalPart * Math.pow(10, -digitCount));
    }

    // parsing normalized notation
    if (getCharacter() == 'e' || getCharacter() == 'E') {
        
      nextCharacter();
      boolean isNegative = false;
        
      if (getCharacter() == '-') {
        nextCharacter();
        isNegative = true;
      } else if (getCharacter() == '+') {
        nextCharacter();
      }

      int exponent = parseUnsignedInteger();

      if (isNegative) {
        exponent *= -1;
      }
        
      result = result * Math.pow(10, exponent);
    }
  
    return result;
  }
  
  protected int parseInteger() throws IOException {
    boolean isNegative = false;
    if (getCharacter() == '-') {
      isNegative = true;
      nextCharacter();
    }
      
    int i = parseUnsignedInteger();

    if (isNegative) {
      return  i *= -1;
    }
      
    return i;
  }

  private int parseUnsignedInteger() throws IOException {
    int value = 0;  
    digitCount = 0;
      
    int c;
    while (isDigit()) {
      c = nextCharacter();
      value = value * 10 + (c - '0');
      ++digitCount;
    }
      
    return value;
  }
  
  protected String parseString(char delimiterCharacter, char escapeCharacter) throws IOException {
    TextPosition positionBeforeParsing = getPosition();      
    nextCharacter();
    StringBuffer sb = new StringBuffer();
  
    while (getCharacter() != delimiterCharacter) {

      if (isEndOfStream()) {
        throw new DataFormatException("Unclosed string", positionBeforeParsing);
      }
        
      if (getCharacter() == escapeCharacter) {
        nextCharacter();
  
        int lookahead = getCharacter();
        
        if (lookahead == delimiterCharacter) {
          sb.append(delimiterCharacter);
          nextCharacter();
        } else if (lookahead == escapeCharacter) {
          sb.append(escapeCharacter);
          nextCharacter();
        } else if (lookahead == 't') {
          sb.append('\t');
          nextCharacter();
        } else if (lookahead == 'r') {
          sb.append('\r');
          nextCharacter();
        } else if (lookahead == 'n') {
          sb.append('\n');
          nextCharacter();
        } else {
          sb.append(escapeCharacter);
          sb.append((char)lookahead);
        }
          
      } else {
        sb.append((char)getCharacter());
        nextCharacter();
      }
    }
    
    // go to next character
    nextCharacter();
    
    return sb.toString();
  }
  
  public void close() throws IOException {
    reader.close();
  }

}
