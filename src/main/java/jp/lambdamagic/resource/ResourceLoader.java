package jp.lambdamagic.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface ResourceLoader {

    public static final String PATH_SEPARATOR = "/";
    
    String getResourceAbsolutePath(String path);
    InputStream getResourceAsStream(String path) throws FileNotFoundException;
    
}