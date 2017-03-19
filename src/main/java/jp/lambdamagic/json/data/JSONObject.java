package jp.lambdamagic.json.data;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.PropertySet;
import jp.lambdamagic.json.JSONDataVisitor;

public class JSONObject implements JSONData, PropertySet<JSONData> {
    
    private Map<String, JSONData> map;
    
    public JSONObject() {
        this.map = new LinkedHashMap<>();
    }

    @Override
    public Optional<JSONData> get(String propertyName) {
        if (propertyName == null) {
            throw new NullArgumentException("propertyName");
        }
        
        return Optional.ofNullable(map.get(propertyName));
    }

    @Override
    public void set(String propertyName, JSONData value) {
        if (propertyName == null) {
            throw new NullArgumentException("propertyName");
        }
        
        if (value == null) {
            throw new NullArgumentException("value");
        }
        
        map.put(propertyName, value);
    }
    
    public void set(String propertyName, String value) {
        set(propertyName, new JSONString(value));
    }

    @Override
    public Set<String> propertyNames() {
        return map.keySet();
    }
    
    public Set<Entry<String, JSONData>> entrySet() {
        return map.entrySet();
    }
    
    @Override
    public void accept(JSONDataVisitor visitor) throws IOException {
        visitor.visit(this);
    }
    
}