package lambdamagic.testing;

public class Assertion extends org.junit.Assert {

	public static void success(String message) {
		assertTrue(message, true);
	}

	public static void raiseNothing(Runnable test) {
		
		try {
			test.run();
			success("raise nothing");
		} catch (Exception ex) {
			fail("Caught exception: " + ex.getClass().getSimpleName());
		}
	}

	public static <T extends Exception> void raiseException(Runnable test, Class<T> expectedExceptionClass) {
		try {
			test.run();
			fail("Expected exception: " + expectedExceptionClass.getSimpleName() + ", but raise nothing");
		} catch (Exception ex) {

			if (ex.getClass() == expectedExceptionClass) {
				success("");
			} else {
				fail("Expected exception: " + expectedExceptionClass.getSimpleName() + ", but actual: " + ex.getClass().getSimpleName());
			}
		}
	}
}
