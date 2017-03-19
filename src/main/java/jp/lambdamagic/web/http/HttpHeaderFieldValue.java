package jp.lambdamagic.web.http;

public final class HttpHeaderFieldValue {

  public static final String CONNECTION_KEEP_ALIVE = "keep-alive";
  public static final String CONNECTION_CLOSE = "close";
  public static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
  public static final String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";
  public static final String CONTENT_ENCODING_GZIP = "gzip";
  public static final String CONTENT_DISPOSITION_FORM_DATA = "form-data";
  public static final String PRAGMA_NO_CACHE = "no-cache";
  public static final String CACHE_CONTROL_NO_CACHE = "private, no-store, no-cache, must-revalidate";
  
  private HttpHeaderFieldValue() {}
  
}
