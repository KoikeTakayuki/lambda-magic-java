package jp.lambdamagic.settings;

import java.util.Map;


public class PropertyString extends InMemoryPropertySet<String> {
	
	protected PropertyString(Map<String, String> propertyMap) {
		super(propertyMap);
	}
	
}
