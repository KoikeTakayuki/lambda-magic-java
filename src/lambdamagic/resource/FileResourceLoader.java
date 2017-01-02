package lambdamagic.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

public class FileResourceLoader implements ResourceLoader {
	
	private String basePath;
	
	public FileResourceLoader() {
		this(null);
	}
	
	public FileResourceLoader(String basePath) {
		this.basePath = basePath;
	}
	
	@Override
	public String getResourceAbsolutePath(String path) {
		return (basePath != null) ? new File(basePath, path).toString() : path;
	}
	
	@Override
	public Optional<InputStream> getResourceAsStream(String path) {
		try {
			return Optional.of(new FileInputStream(getResourceAbsolutePath(path)));
		}
		catch (FileNotFoundException ex) {
			return Optional.empty();
		}
	}
	
}