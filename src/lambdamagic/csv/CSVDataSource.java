package lambdamagic.csv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.pipeline.DataSource;
import lambdamagic.text.TextPosition;

public class CSVDataSource implements DataSource<CSVRow>{

	private CSVParser parser;
	private InputStream inputStream;
	private TextPosition position;

	public CSVDataSource(String filePath) throws FileNotFoundException {
		inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
	}

	@Override
	public Optional<CSVRow> readData() {
		Either<ParseResult<CSVRow>, Exception> rowOrException = parser.parse(inputStream, position);

		if (rowOrException.isRight())
			return Optional.empty();

		ParseResult<CSVRow> result = rowOrException.getLeft();
		position = result.getPosition();

		return Optional.of(result.getResult());
	}

	@Override
	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}