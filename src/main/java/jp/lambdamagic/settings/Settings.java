package jp.lambdamagic.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.PropertySet;

public class Settings implements PropertySet<String> {
  
  private Properties properties;

  public static Settings load(String filePath) throws IOException {
    if (filePath == null) {
      throw new NullArgumentException("filePath");
    }
    
    try (FileInputStream in = new FileInputStream(filePath)) {
      return load(in);
    }
  }
  
  public static Settings load(InputStream inputStream) throws IOException {
    if (inputStream == null) {
      throw new NullArgumentException("inputStream");
    }
    
    Properties properties = new Properties();
    properties.load(inputStream);
    return new Settings(properties);
  }
  
  private Settings(Properties properties) {
    this.properties = properties;
  }
  
  @Override
  public Optional<String> get(String propertyName) {
    if (propertyName == null) {
      throw new NullArgumentException("propertyName");
    }
    
    return Optional.ofNullable(properties.getProperty(propertyName));
  }
  
  @Override
  public void set(String propertyName, String value) {
    if (propertyName == null) {
      throw new NullArgumentException("propertyName");
    }
    
    if (value == null) {
      throw new NullArgumentException("value");
    }

    properties.setProperty(propertyName, value);
  }
  
  @Override
  public Set<String> propertyNames() {
    return properties.stringPropertyNames();
  }
  
  public void save(OutputStream outputStream) throws IOException {
    if (outputStream == null) {
      throw new NullArgumentException("outputStream");
    }
    
    properties.store(outputStream, null);
  }

}
