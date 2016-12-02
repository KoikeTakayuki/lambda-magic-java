package lambdamagic.csv;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lambdamagic.pipeline.DataProcessor;

public class CSVWriter implements DataProcessor<CSVRow, CSVRow> {

	private static final String VALUE_DELIMITER_ESCAPE_STRING = "\"\"";
	
	private OutputStream outputStream;

	public CSVWriter(String filePath) throws IOException {
		outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
	}

	public CSVWriter(String filePath, CSVRow header) throws IOException {
		this(filePath);
		writeCSVRow(header);
	}

	@Override
	public CSVRow process(CSVRow data) {
		writeCSVRow(data);
		return data;
	}

	private void writeCSVRow(CSVRow data) {
		try {
			outputStream.write(toCSVString(data).getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
	
	public String toCSVString(CSVRow data) {
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

			if (length > i)
				sb.append(CSVSpecialCharacter.VALUE_SEPARATOR_CHAR + " ");
		}

		sb.append(CSVSpecialCharacter.ROW_SEPARATOR_CHAR);
		return sb.toString();
	}

	private boolean hasSpecialCharacter(String s) {
		return s.indexOf(CSVSpecialCharacter.VALUE_DELIMITER_CHAR) != -1 ||
				s.indexOf(CSVSpecialCharacter.VALUE_SEPARATOR_CHAR) != -1 ||
				s.indexOf(CSVSpecialCharacter.ROW_SEPARATOR_CHAR) != -1;
	}

	private String escape(String s) {
		return CSVSpecialCharacter.VALUE_DELIMITER_CHAR
				+ s.replace(String.valueOf(CSVSpecialCharacter.VALUE_DELIMITER_CHAR), VALUE_DELIMITER_ESCAPE_STRING)
				+ CSVSpecialCharacter.VALUE_DELIMITER_CHAR;
	}
}
