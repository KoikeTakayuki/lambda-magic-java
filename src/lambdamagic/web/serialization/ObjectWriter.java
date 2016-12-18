package lambdamagic.web.serialization;

import java.io.Closeable;
import java.io.IOException;

public interface ObjectWriter extends Closeable {

	void writeObject(Object obj) throws IOException;
	void flush() throws IOException;
}
