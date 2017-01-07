package jp.lambdamagic.settings;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.functional.Either;
import jp.lambdamagic.parsing.Parser;

public abstract class PropertyStringParser<T extends PropertyString> implements Parser<T> {
	
	private static final String PROPERTY_DELIMITER = ";";
	private static final String PROPERTY_SEPARATOR = "=";

	private String string;
	private Map<String, String> propertyMap;
	
	public PropertyStringParser(String string) {
		if (string == null) {
			throw new NullArgumentException("string");
		}
		
		propertyMap = new LinkedHashMap<>();
	}
	
	protected Either<Map<String, String>, PropertyStringFormatException> parsePropertyStringToMap() {
		String[] properties = string.split(PROPERTY_DELIMITER);
		
		for (String property : properties) {
			int index = property.indexOf(PROPERTY_SEPARATOR);

			if (index == -1) {
				return Either.right(new PropertyStringFormatException("string \"" + property + "\" has no property separator " + PROPERTY_SEPARATOR ));
			}
			
			String propertyName = property.substring(0, index).trim();
			String propertyValue = property.substring(index + PROPERTY_SEPARATOR.length()).trim();
			propertyMap.put(propertyName, propertyValue);
		}

		return Either.left(propertyMap);
	}

}
