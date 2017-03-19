package jp.lambdamagic.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CharactersTest {
    
    @Test
    public void isNewLine_returnTrueWhenNewLineCharacaterIsGiven() {
        assertThat(Characters.isNewLine(Characters.NEW_LINE), is(true));
    }
    
    @Test
    public void isNewLine_returnFalseWhenNonNewLineCharacaterIsGiven() {
        assertThat(Characters.isNewLine('a'), is(false));
    }
    
    @Test
    public void isWhitespace_returnTrueWhenWhitespaceCharacaterIsGiven() {
        assertThat(Characters.isWhitespace(Characters.SPACE), is(true));
        assertThat(Characters.isWhitespace(Characters.TAB), is(true));
        assertThat(Characters.isWhitespace(Characters.CARRIAGE_RETURN), is(true));
        assertThat(Characters.isWhitespace(Characters.NEW_LINE), is(true));
    }
    
    @Test
    public void isWhitespace_returnFalseWhenNonWhitespaceCharacaterIsGiven() {
        assertThat(Characters.isWhitespace('a'), is(false));
    }

    @Test
    public void isAlphabetic_returnTrueWhenAlpabeticCharacterIsGiven() {
        assertThat(Characters.isAlphabetic('A'), is(true));
    }
    
    @Test
    public void isAlphabetic_returnFalseWhenNonAlpabeticCharacterIsGiven() {
        assertThat(Characters.isAlphabetic('0'), is(false));
    }
    
    @Test
    public void isDigit_returnTrueWhenDigitCharacterIsGiven() {
        assertThat(Characters.isDigit('0'), is(true));
    }
    
    @Test
    public void isDigit_returnFalseWhenNonDigitCharacterIsGiven() {
        assertThat(Characters.isDigit('a'), is(false));
    }
    
    @Test
    public void isEndOfStream_returnTrueWhenEndOfStreamCharacterIsGiven() {
        assertThat(Characters.isEndOfStream(Characters.END_OF_STREAM_CODE), is(true));
    }
    
    @Test
    public void isEndOfStream_returnFalseWhenNonEndOfStreamCharacterIsGiven() {
        assertThat(Characters.isEndOfStream('A'), is(false));
    }

}
