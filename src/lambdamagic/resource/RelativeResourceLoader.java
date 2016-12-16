package lambdamagic.resource;

import java.io.InputStream;
import java.util.Optional;

import lambdamagic.NullArgumentException;

public class RelativeResourceLoader implements ResourceLoader {

	private ResourceLoader baseObject;
	private String basePath;

	@Override
	public String getResourceAbsolutePath(String path) {
		return baseObject.getResourceAbsolutePath(basePath + PATH_SEPARATOR + path);
	}
	
	@Override
	public Optional<InputStream> getResourceAsStream(String path) {
		return baseObject.getResourceAsStream(basePath + PATH_SEPARATOR + path);
	}
	
	public RelativeResourceLoader(ResourceLoader baseObject, String basePath) {
		if (baseObject == null)
			throw new NullArgumentException("baseObject");
		
		if (basePath == null)
			throw new NullArgumentException("basePath");
		
		this.baseObject = baseObject;
		this.basePath = basePath;
	}
}
