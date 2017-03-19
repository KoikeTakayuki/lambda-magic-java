package jp.lambdamagic.web.http;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.InMemoryPropertySet;

public final class HttpCookie extends InMemoryPropertySet<String>{

    private static final String PROPERTY_DELIMITER = ";";
    private static final String PROPERTY_SEPARATOR = "=";
    private static final String TRUE_STRING = "true";
    
    public static HttpCookie parse(String cookieString) {
        if (cookieString == null) {
            throw new NullArgumentException("cookieString");
        }
        
        HttpCookie cookie = new HttpCookie();
        String[] properties = cookieString.split(PROPERTY_DELIMITER);
        
        for (String ps : properties) {
            int index = ps.indexOf(PROPERTY_SEPARATOR);
            
            if (index == -1) {
                cookie.set(ps.trim(), TRUE_STRING);
                continue;
            }
                
            String propertyName = ps.substring(0, index).trim();
            String propertyValue = ps.substring(index + PROPERTY_SEPARATOR.length()).trim();
            cookie.set(propertyName, propertyValue);
        }
        
        return cookie;
    }
    
    private HttpCookie() {}
    
}