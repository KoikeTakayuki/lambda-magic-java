package lambdamagic.text;

import lambdamagic.NullArgumentException;

public final class Strings {
	
	public static String EMPTY_STRING = "";

	public static boolean isNullOrEmpty(String s) {
		return ((s == null) || s.isEmpty());
	}
	
	public static String crop(String targetString, String startString, String endString) {
		if (targetString == null)
			throw new NullArgumentException("targetString");

		if (startString == null)
			throw new NullArgumentException("startString");

		if (endString == null)
			throw new NullArgumentException("endString");

		int startIndex = targetString.indexOf(startString) + startString.length();
		int endIndex = targetString.indexOf(endString, startIndex);

		if (startIndex == -1 || endIndex == -1)
			return Strings.EMPTY_STRING;

		return targetString.substring(startIndex, endIndex);
	}

	private Strings() {
	}
}
