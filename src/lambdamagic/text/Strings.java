package lambdamagic.text;

import java.util.Iterator;

import lambdamagic.NullArgumentException;
import lambdamagic.OutOfRangeArgumentException;
import lambdamagic.collection.iterator.Iterables;

public final class Strings {
	
	public static String EMPTY_STRING = "";

	public static boolean isNullOrEmpty(String s) {
		return ((s == null) || s.isEmpty());
	}
	
	public static String getNullIfEmpty(String s) {
		return (s == null) ? null : (s.isEmpty() ? null : s); 
	}
	
	public static String getEmptyIfNull(String s) {
		return (s == null) ? "" : s; 
	}
	
	public static String join(String separator, Iterable<String> strings) {
		if (separator == null) {
			throw new NullArgumentException("separator");
		}
		
		if (strings == null) {
			throw new NullArgumentException("strings");
		}
		
		Iterator<String> it = strings.iterator();

		if (!it.hasNext()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(it.next());
		
		while (it.hasNext()) {
			sb.append(separator);
			sb.append(it.next());
		}
		return sb.toString();
	}
	
	public static String join(String separator, String... strings) {
		return join(separator, Iterables.asIterable(strings));
	}
	
	public static String concat(Iterable<String> strings) {
		if (strings == null)
			throw new NullArgumentException("strings");
		
		StringBuffer sb = new StringBuffer();

		for (String s : strings)
			sb.append(s);
		
		return sb.toString();
	}
	
	public static String concat(String... strings) {
		return concat(Iterables.asIterable(strings));
	}
	
	public static String truncate(String self, int length) {
		if (self == null) {
			throw new NullArgumentException("self");
		}
		
		if (length <= 0) {
			throw new OutOfRangeArgumentException("length", "length <= 0");
		}
		
		if (self.length() <= length) {
			return self;
		}

		return (length <= 3) ? self.substring(0, length)
				: self.substring(0, length - 3) + "...";
	}
	
	public static String padLeft(String self, char c, int length) {
		return paddingString(self, c, length) + self;
	}
	
	public static String padRight(String self, char c, int length) {
		return self + paddingString(self, c, length);
	}
	

	
	public static String crop(String targetString, String startString, String endString) {
		if (targetString == null) {
			throw new NullArgumentException("targetString");
		}
		
		if (startString == null) {
			throw new NullArgumentException("startString");
		}
		
		if (endString == null) {
			throw new NullArgumentException("endString");
		}

		int startIndex = targetString.indexOf(startString) + startString.length();
		int endIndex = targetString.indexOf(endString, startIndex);

		if (startIndex == -1 || endIndex == -1) {
			return Strings.EMPTY_STRING;
		}

		return targetString.substring(startIndex, endIndex);
	}
	
	private static String paddingString(String self, char c, int length) {
		if (self == null) {
			throw new NullArgumentException("self");
		}
		
		if (length <= 0) {
			throw new OutOfRangeArgumentException("length", "length <= 0");
		}
		
		int paddingLength = length - self.length();
		
		if (paddingLength <= 0) {
			return "";
		}
		
		char[] characters = new char[paddingLength];
		for (int i = 0; i < characters.length; ++i) {
			characters[i] = c;
		}
		
		return new String(characters);
	}

	private Strings() {}
	
}
