package lambdamagic.json;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lambdamagic.pipeline.DataProcessor;
import lambdamagic.text.Encodings;

public class JSONWriter implements DataProcessor<Object, Object> {

	private Writer writer;

	public JSONWriter(String filePath, String encoding) throws IOException {
		writer = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(new File(filePath)), encoding));
	}

	public JSONWriter(String filePath) throws IOException {
		this(filePath, Encodings.UTF_8);
	}

	@Override
	public Object process(Object data) {
		try {
			write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public void write(Object data) throws IOException {
		
		if (data instanceof Boolean)
			write((Boolean)data);
		else if (data instanceof Number)
			write((Number)data);
		else if (data instanceof String)
			write((String)data);
		else if (data instanceof List)
			write((List<Object>)data);
		else if (data instanceof Map)
			write((Map<String, Object>)data);
		else
			writer.write(JSONParser.JSON_NULL_STRING);
		
		writer.flush();
	}
	
	public void write(Boolean data) throws IOException {
		if (data)
			writer.write(JSONParser.JSON_TRUE_STRING);
		else
			writer.write(JSONParser.JSON_FALSE_STRING);
	}
	
	public void write(Number data) throws IOException {
		writer.write(data.toString());
	}
	
	public void write(String data) throws IOException {
		writer.write(JSONParser.JSON_STRING_DELIMETER_CHAR);
		writer.write(data);
		writer.write(JSONParser.JSON_STRING_DELIMETER_CHAR);
	}
	
	public void write(List<Object> data) throws IOException {
		writer.write(JSONParser.JSON_ARRAY_START_CHAR);
		
		boolean isFirstWrite = true;

		for (Object e : data) {

			if (isFirstWrite) {
				isFirstWrite = false;
			} else {
				writer.write(JSONParser.JSON_ARRAY_VALUE_SEPARATOR_CHAR);
			}

			write(e);
		}

		writer.write(JSONParser.JSON_ARRAY_END_CHAR);
	}
	
	public void write(Map<String, Object> data) throws IOException {
		writer.write(JSONParser.JSON_OBJECT_START_CHAR);
		
		for (Entry<String, Object> e : data.entrySet()) {
			write(e.getKey());
			write(JSONParser.JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR);
			write(e.getValue());
		}

		writer.write(JSONParser.JSON_OBJECT_END_CHAR);
	}
	
	@Override
	public void close() throws IOException {
		writer.close();
	}
}