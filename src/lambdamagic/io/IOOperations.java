package lambdamagic.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import lambdamagic.NullArgumentException;

/**
 * This class contains various methods for I/O operation.
 * 
 *  <p>The methods in this class all throw a {@code IOException}.
 * 
 * @author koiketakayuki
 */
public final class IOOperations {

	public static final int BYTE_BUFFER_LENGTH = 8192;
	public static final int CHAR_BUFFER_LENGTH = 1024;

	/**
	 * Copy bytes from the input stream to the output stream.
	 * Maximum byte length to be copied is specified as the third argument.
	 * 
	 * Note that bytes count read from the input stream
	 * can be larger than the specified maximum byte length
	 * because reading process uses buffer.
	 * 
	 * Buffer size is {@value #BYTE_BUFFER_LENGTH}.
	 * 
	 * @return totalCount  byte count read from the input stream
	 * @throws IOException
	 */
	public static long copy(InputStream inputStream, OutputStream outputStream, long maximumLength) throws IOException {
		if (inputStream == null)
			throw new NullArgumentException("inputStream");
		
		if (outputStream == null)
			throw new NullArgumentException("outputStream");
		
		byte[] buffer = new byte[BYTE_BUFFER_LENGTH];
		int count;
		long totalCount = 0;

		while ((count = inputStream.read(buffer)) != -1) {
			totalCount += count;
			if ((maximumLength != -1) && (totalCount > maximumLength))
				return -1;
			
			outputStream.write(buffer, 0, count);
		}
		return totalCount;
	}
	
	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		if (inputStream == null)
			throw new NullArgumentException("inputStream");
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			copy(inputStream, outputStream, -1);
		}
		finally {
			inputStream.close();
		}
		return outputStream.toByteArray();
	}
	
	public static void writeAllBytes(OutputStream outputStream, byte[] data) throws IOException {
		if (outputStream == null)
			throw new NullArgumentException("outputStream");
		
		if (data == null)
			throw new NullArgumentException("data");
		
		try {
			copy(new ByteArrayInputStream(data), outputStream, -1);
		}
		finally {
			outputStream.close();
		}
	}
	
	public static String readAllText(Reader reader) throws IOException {
		if (reader == null)
			throw new NullArgumentException("reader");
		
		StringBuffer sb = new StringBuffer();
		char[] buffer = new char[CHAR_BUFFER_LENGTH];
		int count;		
		
		try {
			while ((count = reader.read(buffer)) != -1)
				sb.append(buffer, 0, count);
		}
		finally {
			reader.close();
		}
		return sb.toString();
	}
	
	public static void writeAllText(Writer writer, String text) throws IOException {
		if (writer == null)
			throw new NullArgumentException("writer");
		
		if (text == null)
			throw new NullArgumentException("text");
		
		try {
			writer.write(text);
		}
		finally {
			writer.close();
		}
	}
	
	private IOOperations() {
	}
}
