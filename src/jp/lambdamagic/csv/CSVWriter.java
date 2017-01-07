package jp.lambdamagic.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataProcessor;
import jp.lambdamagic.text.Characters;
import jp.lambdamagic.text.Encodings;

public class CSVWriter implements DataProcessor<List<String>, List<String>> {
	
	private static final String VALUE_DELIMITER_ESCAPE_STRING = "\"\"";

	private Writer writer;
	
	public CSVWriter(Writer writer) {
		if (writer == null) {
			throw new NullArgumentException("writer");
		}
		
		this.writer = writer;
	}

	public CSVWriter(String filePath, String encoding) throws IOException {
		if (filePath == null) {
			throw new NullArgumentException("filePath");
		}
		
		if (encoding == null) {
			throw new NullArgumentException("encoding");
		}
		
		this.writer = new BufferedWriter(
						new OutputStreamWriter(
							new FileOutputStream(new File(filePath)), encoding));
	}

	public CSVWriter(String filePath) throws IOException {
		this(filePath, Encodings.UTF_8);
	}

	@Override
	public List<String> process(List<String> data) {		
		writeRow(data);
		return data;
	}

	public void writeRow(List<String> data) {
		if (data == null) {
			throw new NullArgumentException("data");
		}
		
		try {
			writer.write(toCSVString(data));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
	
	private String toCSVString(List<String> data) {
		StringBuffer sb = new StringBuffer();
		int length = data.size();
		int i = 0;

		for (String s : data) {
			++i;

			if (s != null) {

				if (hasSpecialCharacter(s)) {
					sb.append(escape(s));
				} else {
					sb.append(s);
				}

			}

			if (length > i) {
				sb.append(CSVSpecialCharacter.VALUE_SEPARATOR_CHAR);
			}
		}

		sb.append(CSVSpecialCharacter.ROW_SEPARATOR_CHAR);
		return sb.toString();
	}
	
	private static String escape(String string) {
		return CSVSpecialCharacter.VALUE_DELIMITER_CHAR
				+ string.replaceAll(String.valueOf(CSVSpecialCharacter.VALUE_DELIMITER_CHAR), VALUE_DELIMITER_ESCAPE_STRING)
				+ CSVSpecialCharacter.VALUE_DELIMITER_CHAR;
	}
	
	private static boolean hasSpecialCharacter(String s) {
		return s.indexOf(CSVSpecialCharacter.VALUE_DELIMITER_CHAR) != -1 ||
				s.indexOf(CSVSpecialCharacter.VALUE_SEPARATOR_CHAR) != -1 ||
				s.indexOf(CSVSpecialCharacter.ROW_SEPARATOR_CHAR) != -1 ||
				s.indexOf(Characters.CARRIAGE_RETURN) != -1;
	}
}
