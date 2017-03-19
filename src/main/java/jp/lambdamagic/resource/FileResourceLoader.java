package jp.lambdamagic.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jp.lambdamagic.NullArgumentException;

public class FileResourceLoader implements ResourceLoader {
  
  private String basePath;
  
  public FileResourceLoader(String basePath) {
    if (basePath == null) {
      throw new NullArgumentException("basePath");
    }
    
    this.basePath = basePath;
  }
  
  @Override
  public String getResourceAbsolutePath(String path) {
    if (path == null) {
      throw new NullArgumentException("path");
    }
    
    return new File(basePath, path).toString();
  }
  
  @Override
  public InputStream getResourceAsStream(String path) throws FileNotFoundException {
    if (path == null) {
      throw new NullArgumentException("path");
    }
    
    System.out.println(getResourceAbsolutePath(path));
    
    return new FileInputStream(getResourceAbsolutePath(path));
  }
  
}