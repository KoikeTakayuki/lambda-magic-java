package lambdamagic.csv;

import static org.junit.Assert.*;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.test.AssertionMessage;
import lambdamagic.text.TextPositionBuffer;

public class CSVParserTest {

	@Test
	public void createWithNullArgument() {
		try {
			new TextPositionBuffer(null);
			fail(AssertionMessage.expectNullArgumentException());
		} catch (NullArgumentException e) {
			
		} catch (Exception e) {
			fail(AssertionMessage.expectNullArgumentException(e.getClass()));
		}
	}

}
