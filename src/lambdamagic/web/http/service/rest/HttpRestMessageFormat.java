package lambdamagic.web.http.service.rest;

import lambdamagic.NullArgumentException;
import lambdamagic.web.MimeTypes;

public enum HttpRestMessageFormat {

	JSON(MimeTypes.APPLICATION_JAVASCRIPT);
	
	private String mimeType;
	
	public String getMimeType() {
		return mimeType;
	}
	
	private HttpRestMessageFormat(String mimeType) {
		if (mimeType == null)
			throw new NullArgumentException("mimeType");
		
		this.mimeType = mimeType;
	}
}
