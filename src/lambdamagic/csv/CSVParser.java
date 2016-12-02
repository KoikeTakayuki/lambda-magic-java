package lambdamagic.csv;

import java.io.InputStream;

import lambdamagic.data.functional.Either;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.TextPosition;

public class CSVParser implements Parser<CSVRow> {

	@Override
	public Either<ParseResult<CSVRow>, Exception> parse(InputStream inputStream, TextPosition position) {
		// TODO Auto-generated method stub
		return null;
	}

}
