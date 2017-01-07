package jp.lambdamagic.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import jp.lambdamagic.NullArgumentException;


public abstract class Settings implements PropertySet<String> {

	public static Settings load(String filePath) throws FileNotFoundException, IOException {
		if (filePath == null) {
			throw new NullArgumentException("filePath");
		}
		
		try (FileInputStream is = new FileInputStream(filePath)) {
			return load(is);
		}
	}
	
	public static Settings load(InputStream inputStream) throws IOException {
		if (inputStream == null)
			throw new NullArgumentException("inputStream");
		
		Properties properties = new Properties();
		properties.load(inputStream);
		return asSettings(properties);
	}
	
	public static Settings asSettings(final Properties properties) {
		if (properties == null)
			throw new NullArgumentException("properties");
		
		return new Settings() {
			
			@Override
			public void set(String propertyName, String value) {
				properties.setProperty(propertyName, value);
			}
			
			@Override
			public Optional<String> get(String propertyName) {
				String value = properties.getProperty(propertyName);
				
				if (value == null)
					return Optional.empty();

				return Optional.of(value);
			}
			
			@Override
			public Set<String> propertyNames() {
				return properties.stringPropertyNames();
			}
			
			@Override
			public void save(OutputStream outputStream) throws IOException {
				properties.store(outputStream, null);
			}

			@Override
			public boolean save() {
				return true;
			}
		};
	}
	
	public abstract void save(OutputStream outputStream) throws IOException;
	
	public Optional<String> getRequired(String propertyName) {
		return get(propertyName);
	}
	
}
