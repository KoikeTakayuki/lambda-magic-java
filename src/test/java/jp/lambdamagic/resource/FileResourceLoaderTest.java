package jp.lambdamagic.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.IOOperations;

public class FileResourceLoaderTest {
    
    @Test(expected=NullArgumentException.class)
    public void FileResourceLoader_mustThrowNullArgumentExceptionWhenNullBasePathIsGiven() {
        new FileResourceLoader(null);
    }
    
    @Test(expected=NullArgumentException.class)
    public void getResourceAbsolutePath_mustThrowNullArgumentExceptionWhenNullPathIsGiven() {
        FileResourceLoader loader = new FileResourceLoader("test");
        loader.getResourceAbsolutePath(null);
    }
    
    @Test
    public void getResourceAbsolutePath_returnResourceAbsolutePath() {
        FileResourceLoader loader = new FileResourceLoader("test");
        String result = loader.getResourceAbsolutePath("test");
        assertThat(result, is("test/test"));
    }
    
    @Test(expected=NullArgumentException.class)
    public void getResourceAsStream_mustThrowNullArgumentExceptionWhenNullPathIsGiven() throws FileNotFoundException {
        FileResourceLoader loader = new FileResourceLoader("test");
        loader.getResourceAsStream(null);
    }
    
    @Test(expected=FileNotFoundException.class)
    public void getResourceAsStream_mustThrowFileNotFoundExceptionWhenFileNotFound() throws FileNotFoundException {
        FileResourceLoader loader = new FileResourceLoader("test");
        loader.getResourceAsStream("test");
    }
    
    @Test
    public void getResourceAsStream_returnInputStreamOfSpecifiedFile() throws IOException {
        FileResourceLoader loader = new FileResourceLoader(Paths.get("").toAbsolutePath() + "/test/jp/lambdamagic/resource");
        InputStream in = loader.getResourceAsStream("test_input.txt");
        Reader reader = new InputStreamReader(in);
        String result = IOOperations.readAllText(reader);
        assertThat(result, is("test"));
    }

}
