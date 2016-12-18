package lambdamagic.web.serialization.text;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import lambdamagic.data.functional.Either;
import lambdamagic.io.IOOperations;
import lambdamagic.text.Encodings;
import lambdamagic.web.MimeTypes;
import lambdamagic.web.serialization.DataSerializer;
import lambdamagic.web.serialization.ObjectReader;
import lambdamagic.web.serialization.ObjectWriter;
import lambdamagic.web.serialization.StringSerializer;

public class PlainTextDataSerializer implements DataSerializer {
	
	private static final StringSerializer<Object> BASE_OBJECT = new PlainTextStringSerializer();
	
	@Override
	public String getMimeType() {
		return MimeTypes.TEXT_PLAIN;
	}

	@Override
	public String getEncoding() {
		return Encodings.UTF_8;
	}

	@Override
	public ObjectReader createObjectReader(final Reader reader) throws IOException {
		return new ObjectReader() {
			
			@Override
			public Either<Object, ? extends Exception> readObject() {
				try {
					return BASE_OBJECT.fromString(IOOperations.readAllText(reader));
				} catch (IOException e) {
					return Either.right(e);
				}
			}

			@Override
			public void close() throws IOException {
			}
		};
	}

	@Override
	public ObjectWriter createObjectWriter(final Writer writer) throws IOException {
		return new ObjectWriter() {
			
			@Override
			public void writeObject(Object obj) throws IOException {
				writer.write(BASE_OBJECT.toString(obj));
			}
			
			@Override
			public void flush() throws IOException {
				writer.flush();
			}
			
			@Override
			public void close() throws IOException {
			}
		};
	}
}
