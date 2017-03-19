package jp.lambdamagic.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;

import jp.lambdamagic.NullArgumentException;

public class RelativeResourceLoader implements ResourceLoader {

  private ResourceLoader wrapped;
  private String basePath;
  
  public RelativeResourceLoader(ResourceLoader wrapped, String basePath) {
    if (wrapped == null) {
      throw new NullArgumentException("wrapped");
    }
    
    if (basePath == null) {
      throw new NullArgumentException("basePath");
    }
    
    this.wrapped = wrapped;
    this.basePath = basePath;
  }
  
  @Override
  public String getResourceAbsolutePath(String path) {
    if (path == null) {
      throw new NullArgumentException("path");
    }
    
    return wrapped.getResourceAbsolutePath(basePath + PATH_SEPARATOR + path);
  }
  
  @Override
  public InputStream getResourceAsStream(String path) throws FileNotFoundException {
    if (path == null) {
      throw new NullArgumentException("path");
    }
    
    return wrapped.getResourceAsStream(basePath + PATH_SEPARATOR + path);
  }
  
}