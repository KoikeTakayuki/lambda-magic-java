package lambdamagic.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.pipeline.DataSource;
import lambdamagic.text.Encodings;
import lambdamagic.text.TextPosition;

public class CSVDataSource implements DataSource<List<String>>{

	private CSVParser parser;
	private Reader reader;
	private TextPosition position;

	public CSVDataSource(String filePath, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
		if (filePath == null)
			throw new NullArgumentException("filePath");

		reader = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(new File(filePath)), encoding));
		parser = new CSVParser();
		position = TextPosition.initialize();
	}
	
	public CSVDataSource(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		this(filePath, Encodings.UTF_8);
	}

	@Override
	public Optional<List<String>> readData() {
		Either<ParseResult<List<String>>, Exception> rowOrException = parser.parse(reader, position);


		if (rowOrException.isRight()) {
			//rowOrException.getRight().printStackTrace(System.err);
			return Optional.empty();
		}

		ParseResult<List<String>> result = rowOrException.getLeft();
		position = result.getPosition();

		return Optional.of(result.getResult());
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}
}