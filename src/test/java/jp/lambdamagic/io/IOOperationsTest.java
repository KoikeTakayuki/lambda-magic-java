package jp.lambdamagic.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class IOOperationsTest {

	@Test(expected=NullArgumentException.class)
	public void copy_mustThrowNullArgumentExceptionWhenNullInputStreamIsGiven() throws IOException {
		IOOperations.copy(null, new ByteArrayOutputStream(), 1024);
	}
	
	@Test(expected=NullArgumentException.class)
	public void copy_mustThrowNullArgumentExceptionWhenNullOutputStreamIsGiven() throws IOException {
		byte[] buf = "test".getBytes();
		IOOperations.copy(new ByteArrayInputStream(buf), null, 1024);
	}
	
	@Test
	public void copy_copyInputStreamToOutputStream() throws IOException {
		byte[] buf = "test".getBytes();
		OutputStream os = new ByteArrayOutputStream();
		long readCount = IOOperations.copy(new ByteArrayInputStream(buf), os, 1024);
		assertThat(readCount, is(4L));
		assertThat(os.toString(), is("test"));
	}
	
	@Test
	public void copy_failWithSmallerMaximumLengthThanTotalReadCount() throws IOException {
		byte[] buf = "test".getBytes();
		OutputStream os = new ByteArrayOutputStream();
		long copyByteCount = IOOperations.copy(new ByteArrayInputStream(buf), os, 2);
		assertThat(copyByteCount, is(-1L));
		assertThat(os.toString(), is(""));
	}
	
	@Test
	public void copy_DoNotCheckReadCountWhenMinusOneMaximunLengthIsGiven() throws IOException {
		byte[] buf = "test".getBytes();
		OutputStream os = new ByteArrayOutputStream();
		long copyByteCount = IOOperations.copy(new ByteArrayInputStream(buf), os, -1);
		assertThat(copyByteCount, is(4L));
		assertThat(os.toString(), is("test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void readAllBytes_mustThrowNullArgumentExceptionWhenNUllInputStreamIsGiven() throws IOException {
		IOOperations.readAllBytes(null);
	}
	
	@Test
	public void readAllBytes_readAllBytesFromInputStream() throws IOException {
		byte[] result = IOOperations.readAllBytes(new ByteArrayInputStream("test".getBytes()));
		assertThat(new String(result), is("test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void writeAllBytes_mustThrowNullArgumentException() throws IOException {
		IOOperations.writeAllBytes(null, "test".getBytes());
	}
	
	@Test
	public void writeAllBytes_writeAllBytesToOutputStream() throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		IOOperations.writeAllBytes(os, "test".getBytes());
		assertThat(os.toString(), is("test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void readAllText_mustThrowNullArgumentExceptionWhenNullReaderIsGiven() throws IOException {
		IOOperations.readAllText(null);
	}
	
	@Test
	public void readAllText_readAllTextWithReader() throws IOException {
		String result = IOOperations.readAllText(new StringReader("test"));
		assertThat(result, is("test"));
	}
	
	@Test(expected=NullArgumentException.class)
	public void writeAllText_mustThrowNullArgumentExceptionWhenNullWriterIsGiven() throws IOException {
		IOOperations.writeAllText(null, "test");
	}
	
	@Test(expected=NullArgumentException.class)
	public void writeAllText_mustThrowNullArgumentExceptionWhenNullTextIsGiven() throws IOException {
		IOOperations.writeAllText(new StringWriter(), null);
	}
	
	@Test
	public void writeAllText_writeAllTextWithWriter() throws IOException {
		Writer writer = new StringWriter();
		IOOperations.writeAllText(writer, "test");
		assertThat(writer.toString(), is("test"));
	}
	
}
