package jp.lambdamagic.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.io.IOOperations;

public class RelativeResourceLoaderTest {

	@Test(expected=NullArgumentException.class)
	public void ReltativeResourceLoader_mustThrowNullArgumentExceptionWhenNullResourceLoaderIsGiven() {
		new RelativeResourceLoader(null, "test");
	}
	
	@Test(expected=NullArgumentException.class)
	public void ReltativeResourceLoader_mustThrowNullArgumentExceptionWhenNullBasePathIsGiven() {
		new RelativeResourceLoader(new FileResourceLoader("test"), null);
	}
	
	@Test(expected=NullArgumentException.class)
	public void getResourceAbsolutePath_mustThrowNullArgumentExceptionWhenNullPathIsGiven() {
		RelativeResourceLoader loader = new RelativeResourceLoader(new FileResourceLoader("test"), "test");
		loader.getResourceAbsolutePath(null);
	}
	
	@Test
	public void getResourceAbsolutePath_returnResourceAbsolutePath() {
		RelativeResourceLoader loader = new RelativeResourceLoader(new FileResourceLoader("test1"), "test2");
		
		String result = loader.getResourceAbsolutePath("test3");
		assertThat(result, is("test1/test2/test3"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void getResourceAsStream_mustThrowNullArgumentExceptionWhenNullPathIsGiven() throws FileNotFoundException {
		RelativeResourceLoader loader = new RelativeResourceLoader(new FileResourceLoader("test1"), "test2");
		loader.getResourceAsStream(null);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void getResourceAsStream_mustThrowFileNotFoundExceptionWhenFileNotFound() throws FileNotFoundException {
		RelativeResourceLoader loader = new RelativeResourceLoader(new FileResourceLoader("test1"), "test2");
		loader.getResourceAsStream("test3");
	}
	
	@Test
	public void getResourceAsStream_returnInputStreamOfSpecifiedFile() throws IOException {
		RelativeResourceLoader loader = new RelativeResourceLoader(new FileResourceLoader(Paths.get("").toAbsolutePath().toString()), "test/jp/lambdamagic/resource");
		InputStream in = loader.getResourceAsStream("test_input.txt");
		String result = IOOperations.readAllText(new InputStreamReader(in));
		assertThat(result, is("test"));
	}

}
