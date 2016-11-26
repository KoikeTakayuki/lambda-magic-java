package lambdamagic.settings;

import java.util.Map.Entry;

import lambdamagic.NullArgumentException;
import lambdamagic.text.Strings;


public class PropertyString extends InMemoryPropertySet<String> {
	
	private static final String PROPERTY_DELIMITER = ";";
	private static final String PROPERTY_SEPARATOR = "=";
	private static final String TRUE_STRING = Boolean.toString(true);

	protected PropertyString() {
	}

	protected PropertyString(String s) {
		parse(s, this);
	}

	public static PropertyString parse(String s) {
		return parse(s, new PropertyString());
	}

	private static PropertyString parse(String s, PropertyString propertyString) {
		if (s == null)
			throw new NullArgumentException("s");
		
		String[] properties = s.split(PROPERTY_DELIMITER);
		
		for (String property : properties) {
			int index = property.indexOf(PROPERTY_SEPARATOR);
			
			if (index == -1) {
				propertyString.set(property.trim(), TRUE_STRING);
				continue;
			}
				
			String propertyName = property.substring(0, index).trim();
			String propertyValue = property.substring(index + PROPERTY_SEPARATOR.length()).trim();
			propertyString.set(propertyName, propertyValue);
		}
		
		return propertyString;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (Entry<String, String> e : getPropertyMap().entrySet()) {
			
			if (Strings.isNullOrEmpty(e.getValue()))
				continue;
			
			sb.append(e.getKey());
						
			if (!e.getValue().equals(TRUE_STRING)) {
				sb.append(PROPERTY_SEPARATOR);
				sb.append(e.getValue());
			}
			sb.append(PROPERTY_DELIMITER);
		}
		return sb.toString();
	}
}
