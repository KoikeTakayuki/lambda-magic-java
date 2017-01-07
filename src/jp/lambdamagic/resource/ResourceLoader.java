package jp.lambdamagic.resource;

import java.io.InputStream;
import java.util.Optional;

public interface ResourceLoader {

	public static final String PATH_SEPARATOR = "/";
	
	String getResourceAbsolutePath(String path);
	Optional<InputStream> getResourceAsStream(String path);
}