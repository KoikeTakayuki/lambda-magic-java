package jp.lambdamagic.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jp.lambdamagic.InvalidArgumentException;

public class TextPositionTest {

    @Test(expected=InvalidArgumentException.class)
    public void TextPosition_mustThrowInvalidArgumentExceptionWhenNonPositiveLineNumberIsGiven() {
        new TextPosition(0, 0);
    }
    
    @Test(expected=InvalidArgumentException.class)
    public void TextPosition_mustThrowInvalidArgumentExceptionWhenNegativeColumnNumberIsGiven() {
        new TextPosition(1, -1);
    }
    
    @Test
    public void getLineNumber_returnGivenLineNumber() {
        TextPosition position = new TextPosition(1, 0);
        assertThat(position.getLineNumber(), is(1));
    }
    
    @Test
    public void getColumnNumber_returnGivenColumnNumber() {
        TextPosition position = new TextPosition(1, 0);
        assertThat(position.getColumnNumber(), is(0));
    }
    
    @Test
    public void equals_returnTrueWhenSamePositionIsGiven() {
        TextPosition position1 = new TextPosition(1, 0);
        TextPosition position2 = new TextPosition(1, 0);
        assertThat(position1.equals(position2), is(true));
    }
    
    @Test
    public void equals_returnFalseWhenDifferentPositionIsGiven() {
        TextPosition position1 = new TextPosition(1, 0);
        TextPosition position2 = new TextPosition(1, 1);
        assertThat(position1.equals(position2), is(false));
    }
    
    @Test
    public void equals_returnFalseWhenNonTextPositionObjectIsGiven() {
        TextPosition position1 = new TextPosition(1, 0);
        List<?> list = new ArrayList<>();
        assertThat(position1.equals(list), is(false));
    }

}
