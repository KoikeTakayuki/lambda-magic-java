package lambdamagic.web.serialization;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface DataSerializer {
	String getMimeType();
	String getEncoding();
	ObjectReader createObjectReader(Reader reader) throws IOException;
	ObjectWriter createObjectWriter(Writer writer) throws IOException;
}
