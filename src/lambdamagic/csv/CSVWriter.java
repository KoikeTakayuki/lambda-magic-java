package lambdamagic.csv;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import lambdamagic.pipeline.DataProcessor;

public class CSVWriter implements DataProcessor<List<String>, List<String>> {

	private OutputStream outputStream;

	public CSVWriter(String filePath) throws IOException {
		outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
	}

	public CSVWriter(String filePath, List<String> header) throws IOException {
		this(filePath);
		writeRow(header);
	}

	@Override
	public List<String> process(List<String> data) {
		writeRow(data);
		return data;
	}

	private void writeRow(List<String> data) {
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
	
	public String toCSVString(List<String> data) {
		StringBuffer sb = new StringBuffer();
		int length = data.size();
		int i = 0;

		for (String s : data) {
			++i;

			if (s != null) {

				if (CSVSpecialCharacter.hasSpecialCharacter(s))
					sb.append(CSVSpecialCharacter.escape(s));
				else
					sb.append(s);				

			}

			if (length > i)
				sb.append(CSVSpecialCharacter.VALUE_SEPARATOR_CHAR);
		}

		sb.append(CSVSpecialCharacter.ROW_SEPARATOR_CHAR);
		return sb.toString();
	}
}
