package jp.lambdamagic.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.EndOfStreamException;
import jp.lambdamagic.parsing.ParserBase;
import jp.lambdamagic.text.Strings;
import jp.lambdamagic.text.TextPosition;

public class CSVParser extends ParserBase<List<String>> {
    
    public static final char ROW_SEPARATOR_CHAR = '\n';
    public static final char VALUE_SEPARATOR_CHAR = ',';
    public static final char VALUE_DELIMITER_CHAR = '"';
    public static final char VALUE_ESCAPE_CHAR = '\\';
    public static final char[] DEFAULT_VALUE_SEPARATORS = new char[] { VALUE_SEPARATOR_CHAR, ROW_SEPARATOR_CHAR };
    
    public static CSVParser fromString(String string) throws IOException {
        if (string == null) {
            throw new NullArgumentException("string");
        }
        
        return new CSVParser(new StringReader(string));
    }

    public CSVParser(Reader reader) throws IOException {
        super(reader);
        ignoreBOM();
    }

    @Override
    public List<String> parse() throws IOException {
        return parseRow();
    }

    private List<String> parseRow() throws IOException {
        if (isEndOfStream()) {
            throw new EndOfStreamException();
        }
        
        skipWhitespacesUntilNewline();
        
        List<String> result = new ArrayList<String>();

            //empty row
        if (isEndOfLine()) {
            nextCharacter();
            return result;
        }
            
        if (isEndOfStream()) {
            return result;
        }

        // parsing first element
        String value = parseCSVValue();
        result.add(value);

        // parsing remaining element
        while (true) {
            skipWhitespacesUntilNewline();

            if (isEndOfStream()) {
                break;
            }

            if (isEndOfLine()) {
                nextCharacter();
                break;
            }

            if (getCharacter() != VALUE_SEPARATOR_CHAR) {
                throw new CSVFormatException("CSV value separator '" + VALUE_SEPARATOR_CHAR + "' is expected, but actual '" + (char)getCharacter() + "' is given at " + getPosition());
            }

            nextCharacter();
            skipWhitespacesUntilNewline();

            if (isEndOfLine()) {
                result.add(Strings.EMPTY_STRING);
                nextCharacter();
                break;
            }

            value = parseCSVValue();
                
            result.add(value);
        }

        return result;
    }

    public String parseCSVValue() throws IOException {
        if (getCharacter() == VALUE_DELIMITER_CHAR) {
            return parseCSVString();
        }

        return parseUntil(DEFAULT_VALUE_SEPARATORS).trim();
    }

    private String parseCSVString() throws IOException {
        TextPosition positionBeforeParsing = getPosition();
        nextCharacter();
        StringBuffer sb = new StringBuffer();
    
        while (true) {

            if (isEndOfStream()) {
                throw new CSVFormatException("Unclosed string at " + positionBeforeParsing);
            }
                
            if (getCharacter() == VALUE_ESCAPE_CHAR) {
                nextCharacter();
    
                int lookahead = getCharacter();

                if (lookahead == VALUE_ESCAPE_CHAR) {
                    sb.append(VALUE_ESCAPE_CHAR);
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
                    sb.append(VALUE_ESCAPE_CHAR);
                    sb.append((char)lookahead);
                }
                    
            // double quote escaping or end of string
            } else if (getCharacter() == VALUE_DELIMITER_CHAR) {
                nextCharacter();

                //double quote escaping
                if (getCharacter() == VALUE_DELIMITER_CHAR) {
                    sb.append(VALUE_DELIMITER_CHAR);
                    nextCharacter();
                        
                //end of string
                } else {
                    return sb.toString();
                }

            } else {
                sb.append((char)getCharacter());
                nextCharacter();
            }
        }

    }

    private void ignoreBOM() throws IOException {
        reader.mark(4);
        
        if (reader.read() != '\ufeff') {
            reader.reset(); 
        }
    }

}