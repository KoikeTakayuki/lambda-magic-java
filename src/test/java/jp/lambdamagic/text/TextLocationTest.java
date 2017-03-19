package jp.lambdamagic.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class TextLocationTest {

  @Test(expected=NullArgumentException.class)
  public void TextLocation_mustThrowNullArgumentExceptionWhenNullSourceNameIsGiven() {
    new TextLocation(null, TextPosition.initialize());
  }
  
  @Test(expected=NullArgumentException.class)
  public void TextLocation_mustThrowNullArgumentExceptionWhenNullStartPositionIsGiven() {
    new TextLocation("sourceName", null, TextPosition.initialize());
  }

  @Test(expected=NullArgumentException.class)
  public void TextLocation_mustThrowNullArgumentExceptionWhenNullEndPositionIsGiven() {
    new TextLocation("sourceName", TextPosition.initialize(), null);
  }
  
  @Test
  public void getSourceName_returnGivenSourceName() {
    TextLocation location = new TextLocation("sourceName", TextPosition.initialize());
    assertThat(location.getSourceName(), is("sourceName"));
  }
  
  @Test
  public void getStartPosition_returnGivenStartPosition() {
    TextLocation location = new TextLocation("sourceName", TextPosition.initialize());
    assertThat(location.getStartPosition(), is(TextPosition.initialize()));
  }
  
  @Test
  public void getEndPosition_returnGivenEndPosition() {
    TextLocation location = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    assertThat(location.getEndPosition(), is(new TextPosition(10, 10)));
  }
  
  @Test
  public void equals_returnTrueWhenSameTextLocationIsGiven() {
    TextLocation location1 = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    TextLocation location2 = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    assertThat(location1.equals(location2), is(true));
  }
  
  @Test
  public void equals_returnFalseWhenDifferentTextLocationIsGiven() {
    TextLocation location1 = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    TextLocation location2 = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(20, 20));
    assertThat(location1.equals(location2), is(false));
  }
  
  @Test
  public void equals_returnFalseWhenNonTextLocationObjectIsGiven() {
    TextLocation location1 = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    List<?> location2 = new ArrayList<>();
    assertThat(location1.equals(location2), is(false));
  }
  
  @Test
  public void toString_returnSourceNameAndPositionsString() {
    TextLocation location = new TextLocation("sourceName", TextPosition.initialize(), new TextPosition(10, 10));
    assertThat(location.toString(), is("[sourceName, (1, 0), (10, 10)]"));
  }
  
  @Test
  public void toString_returnSourceNameAndPositionStringWhenStartAndEndPositionIsSame() {
    TextLocation location = new TextLocation("sourceName", TextPosition.initialize(), TextPosition.initialize());
    assertThat(location.toString(), is("[sourceName, (1, 0)]"));
  }
  
}
