package jp.lambdamagic.web.serialization.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import jp.lambdamagic.json.JSONParser;
import jp.lambdamagic.json.JSONWriter;
import jp.lambdamagic.text.Encodings;
import jp.lambdamagic.web.MimeTypes;
import jp.lambdamagic.web.serialization.DataSerializer;
import jp.lambdamagic.web.serialization.ObjectReader;
import jp.lambdamagic.web.serialization.ObjectWriter;

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
		return null;
	}

	@Override
	public ObjectWriter createObjectWriter(Writer writer) throws IOException {
		return null;
	}

}
