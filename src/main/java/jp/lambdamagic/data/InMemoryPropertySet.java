package jp.lambdamagic.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jp.lambdamagic.NullArgumentException;

public class InMemoryPropertySet<T> implements PropertySet<T> {
    
    private Map<String, T> map;
    
    public InMemoryPropertySet() {
        this.map = new HashMap<>();
    }

    @Override
    public Optional<T> get(String propertyName) {
        if (propertyName == null) {
            throw new NullArgumentException("propertyName");
        }
        
        return Optional.ofNullable(map.get(propertyName));
    }

    @Override
    public void set(String propertyName, T value) {
        if (propertyName == null) {
            throw new NullArgumentException("propertyName");
        }
        
        if (value == null) {
            throw new NullArgumentException("value");
        }
        
        map.put(propertyName, value);
    }

    @Override
    public Set<String> propertyNames() {
        return map.keySet();
    }

}
