package jp.lambdamagic.json.data;

import java.io.IOException;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.JSONDataVisitor;

public class JSONString implements JSONData{
    
    private String value;

    public JSONString(String value) {
        if (value == null) {
            throw new NullArgumentException("value");
        }
        
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public void accept(JSONDataVisitor visitor) throws IOException {
        visitor.visit(this);
    }
    
}
