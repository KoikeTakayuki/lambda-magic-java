package lambdamagic.web.serialization.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import lambdamagic.data.functional.Either;
import lambdamagic.json.JSONParser;
import lambdamagic.json.JSONWriter;
import lambdamagic.text.Encodings;
import lambdamagic.web.MimeTypes;
import lambdamagic.web.serialization.DataSerializer;
import lambdamagic.web.serialization.ObjectReader;
import lambdamagic.web.serialization.ObjectWriter;

public class JSONDataSerializer implements DataSerializer {	

	@Override
	public String getMimeType() {
		return MimeTypes.APPLICATION_JAVASCRIPT;
	}

	@Override
	public String getEncoding() {
		return Encodings.UTF_8;
	}

	@Override
	public ObjectReader createObjectReader(Reader reader) throws IOException {
		return new JSONParser(reader);
	}

	@Override
	public ObjectWriter createObjectWriter(Writer writer) throws IOException {
		return new JSONWriter(writer);
	}

}
