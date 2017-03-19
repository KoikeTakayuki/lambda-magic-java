package jp.lambdamagic.web.http.service.rest;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.web.MimeTypes;

public enum HttpRestMessageFormat {
    
    JSON(MimeTypes.APPLICATION_JAVASCRIPT);
    
    private String mimeType;
    
    private HttpRestMessageFormat(String mimeType) {
        if (mimeType == null) {
            throw new NullArgumentException("mimeType");
        }
        
        this.mimeType = mimeType;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
}
