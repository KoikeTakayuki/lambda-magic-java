package jp.lambdamagic.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.pipeline.DataSource;
import jp.lambdamagic.text.Encodings;

public class JSONDataSource implements DataSource<JSONData> {

	private JSONParser parser;
	private DataSource<JSONData> jsonDataSource;
	
	public JSONDataSource(Reader reader) throws IOException {
		setParser(new JSONParser(reader));
	}

	public JSONDataSource(String filePath, String encoding) throws IOException {
		if (filePath == null) {
			throw new NullArgumentException("filePath");
		}
		
		if (encoding == null) {
			throw new NullArgumentException("encoding");
		}
		
		Reader reader = new BufferedReader(
							new InputStreamReader(
								new FileInputStream(new File(filePath)), encoding));

		setParser(new JSONParser(reader));
	}
	
	
	private void setParser(JSONParser parser) throws IOException {
		this.parser = parser;
		JSONData data = parser.parse();
		
		if (data instanceof JSONArray) {
			this.jsonDataSource = DataSource.asDataSource((JSONArray)data);
		} else {
			this.jsonDataSource = DataSource.asDataSource(data);
		}
	}

	public JSONDataSource(String filePath) throws IOException {
		this(filePath, Encodings.UTF_8);
	}
	
	public static JSONDataSource fromString(String string) throws IOException {
		if (string == null) {
			throw new NullArgumentException("string");
		}
		
		return new JSONDataSource(new StringReader(string));
	}

	@Override
	public Optional<JSONData> readData() {
		return jsonDataSource.readData();
	}
	
	@Override
	public void close() throws IOException {
		parser.close();
	}

}
