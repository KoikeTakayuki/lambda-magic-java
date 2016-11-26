package lambdamagic.settings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import lambdamagic.NullArgumentException;

public class InMemoryPropertySet<T> implements PropertySet<T> {
	
	private Map<String, T> propertyMap;
	
	public Map<String, T> getPropertyMap() {
		return propertyMap;
	}
	
	public InMemoryPropertySet() {
		this.propertyMap = new LinkedHashMap<String, T>();
	}
	
	protected InMemoryPropertySet(Map<String, T> propertyMap) {
		if (propertyMap == null)
			throw new NullArgumentException("propertyMap");
		
		this.propertyMap = propertyMap;
	}

	@Override
	public Set<String> propertyNames() {
		return getPropertyMap().keySet();
	}
	
	@Override
	public T get(String propertyName) {
		return getPropertyMap().get(propertyName);
	}

	@Override
	public void set(String propertyName, T value) {
		if (propertyName == null)
			throw new NullArgumentException("propertyName");
		
		getPropertyMap().put(propertyName, value);
	}

	@Override
	public boolean save() {
		return true;
	}
}
