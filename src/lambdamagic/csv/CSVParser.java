package lambdamagic.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import lambdamagic.data.functional.Either;
import lambdamagic.io.EndOfStreamException;
import lambdamagic.parsing.ParseResult;
import lambdamagic.parsing.Parser;
import lambdamagic.text.Characters;
import lambdamagic.text.TextPosition;
import lambdamagic.text.TextPositionBuffer;

public class CSVParser implements Parser<List<String>> {
	
	@Override
	public Either<ParseResult<List<String>>, Exception> parse(Reader reader, TextPosition position) {

		BufferedReader bufferedReader;

		if (reader instanceof BufferedReader)
			bufferedReader = (BufferedReader)reader;
		else
			bufferedReader = new BufferedReader(reader);

		try {
			TextPositionBuffer textPositionBuffer = new TextPositionBuffer(position);
			List<String> result = new ArrayList<String>();
			String row = bufferedReader.readLine();
			StringBuffer sb = new StringBuffer();
			boolean readCSVString = false;
			boolean readEscapedQuotation = false;
			int currentCharacter;

			if (row == null)
				return Either.right(new EndOfStreamException());

			for (int i = 0; i < row.length(); ++i) {
				currentCharacter = row.charAt(i);
				textPositionBuffer.update((char)currentCharacter);
				
				// start reading CSV string
				if (!readCSVString && currentCharacter == CSVSpecialCharacter.VALUE_DELIMITER_CHAR) {
					readCSVString = true;
					continue;
				}

				//reading CSV string
				if (readCSVString) {

					if (currentCharacter == CSVSpecialCharacter.VALUE_DELIMITER_CHAR) {

						//ignore if after reading escaped quotation
						if (readEscapedQuotation) {
							readEscapedQuotation = false;
							continue;
						}

						//escaped double quotation
						if (i + 1 < row.length() && row.charAt(i + 1) == CSVSpecialCharacter.VALUE_DELIMITER_CHAR) {
							sb.append(CSVSpecialCharacter.VALUE_DELIMITER_CHAR);
							readEscapedQuotation = true;

						//finish reading CSV string
						} else {
							readCSVString = false;
						}	
						
					} else {
						sb.append((char)currentCharacter);
					}


				} else if (currentCharacter == CSVSpecialCharacter.VALUE_SEPARATOR_CHAR) {

					result.add(sb.toString());
					sb.setLength(0);

				} else {
					sb.append((char)currentCharacter);
				}

			}
			
			if (readCSVString)
				return Either.right(new CSVFormatException("End of row before closing double quotation at position " + textPositionBuffer.toTextPosition()));
			
			if (sb.length() > 0)
				result.add(sb.toString());
	
			textPositionBuffer.update(CSVSpecialCharacter.ROW_SEPARATOR_CHAR);
			return Either.left(new ParseResult<List<String>>(result, textPositionBuffer.toTextPosition()));

		} catch (IOException e) {
			return Either.right(e);
		}
	}

}