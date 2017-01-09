package jp.lambdamagic.settings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.PropertySet;

public class InMemoryPropertySet<T> implements PropertySet<T> {
	
	private Map<String, T> propertyMap;
	
	protected InMemoryPropertySet(Map<String, T> propertyMap) {
		if (propertyMap == null) {
			throw new NullArgumentException("propertyMap");
		}
		
		this.propertyMap = propertyMap;
	}
	
	public Map<String, T> getPropertyMap() {
		return propertyMap;
	}
	
	public InMemoryPropertySet() {
		this.propertyMap = new LinkedHashMap<String, T>();
	}

	@Override
	public Set<String> propertyNames() {
		return getPropertyMap().keySet();
	}
	
	@Override
	public Optional<T> get(String propertyName) {
		T result = getPropertyMap().get(propertyName);
		
		if (result == null) {
			return Optional.empty();
		}
		
		return Optional.of(result);
	}

	@Override
	public void set(String propertyName, T value) {
		if (propertyName == null)
			throw new NullArgumentException("propertyName");
		
		getPropertyMap().put(propertyName, value);
	}

}
