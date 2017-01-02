package lambdamagic.web.serialization.text;

import lambdamagic.data.functional.Either;
import lambdamagic.io.DataFormatException;
import lambdamagic.web.serialization.StringSerializer;

public class PlainTextStringSerializer implements StringSerializer<Object> {

	private static final String NULL_VALUE_STRING = "null";
	private static final String BOOLEAN_TRUE_STRING = "true";
	private static final String BOOLEAN_FALSE_STRING = "false";
	
	@Override
	public Either<Object, DataFormatException> fromString(String s) {
		Number value;

		try {
			value = Integer.parseInt(s);
			return Either.left(value);
		} catch (NumberFormatException ex) {}
		
		try {
			value = Double.parseDouble(s);
			return Either.left(value);
		} catch (NumberFormatException ex) {}
		
		switch (s) {
			case NULL_VALUE_STRING:
				return null;
			case BOOLEAN_TRUE_STRING:
				return Either.left(true);
			case BOOLEAN_FALSE_STRING:
				return Either.left(false);
			default:
				return Either.left(s);
		}
	}

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return NULL_VALUE_STRING;
		} else if (obj instanceof Boolean) {
			return ((Boolean)obj) ? BOOLEAN_TRUE_STRING : BOOLEAN_FALSE_STRING;
		} else {
			return obj.toString();
		}
	}
	
}
