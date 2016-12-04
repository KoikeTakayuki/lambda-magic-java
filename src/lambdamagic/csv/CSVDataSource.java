package lambdamagic.csv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.pipeline.DataSource;
import lambdamagic.text.TextPosition;

public class CSVDataSource implements DataSource<List<String>>{

	private CSVParser parser;
	private InputStream inputStream;
	private TextPosition position;

	public CSVDataSource(String filePath) throws FileNotFoundException {
		if (filePath == null)
			throw new NullArgumentException("filePath");

		inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
		parser = new CSVParser();
		position = TextPosition.initialize();
	}

	@Override
	public Optional<List<String>> readData() {
		Either<ParseResult<List<String>>, Exception> rowOrException = parser.parse(inputStream, position);


		if (rowOrException.isRight()) {
			rowOrException.getRight().printStackTrace(System.err);
			return Optional.empty();
		}

		ParseResult<List<String>> result = rowOrException.getLeft();
		position = result.getPosition();

		return Optional.of(result.getResult());
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}