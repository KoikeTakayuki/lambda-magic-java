package lambdamagic.test;

import lambdamagic.NullArgumentException;

public class AssertionMessage {
	
	public static <T extends Exception> String notExpectException(Class<T> actual) {
		return "doesn't expect Exception, but " + actual.getSimpleName() + " has occured";
	}

	public static <T extends Exception> String expectException(Class<T> expected) {
		return expected.getSimpleName() + " is expected, but no exception has occured";
	}
	
	public static <T extends Exception, U extends Exception> String
		expectException(Class<T> expected, Class<U> actual) {
		return expected.getSimpleName() + " is expected, but actual exception is " + actual.getSimpleName();
	}
	
	public static String expectNullArgumentException() {
		return expectException(NullArgumentException.class);
	}
	
	public static <T extends Exception> String expectNullArgumentException(Class<T> actual) {
		return expectException(NullArgumentException.class, actual);
	}

}
