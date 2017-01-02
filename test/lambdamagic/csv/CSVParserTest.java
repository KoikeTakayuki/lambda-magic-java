package lambdamagic.csv;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lambdamagic.NullArgumentException;
import lambdamagic.data.functional.Either;
import lambdamagic.io.EndOfStreamException;

public class CSVParserTest {

	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void CSVParser_mustThrowExceptionWhenNullReaderIsGiven() throws IOException {
		new CSVParser(null);
	}
	
	@Test(expected=NullArgumentException.class)
	public void fromString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() throws IOException {
		CSVParser.fromString(null);
	}
	
	@Test
	public void parse_returnCSVFormatExceptionWhenInvalidCSVFormat() throws IOException {
		Either<List<String>, Exception> parseResult = CSVParser.fromString("\"test").parse();

		assertThat(parseResult.isRight(), is(true));
		assertThat(parseResult.getRight(), is(instanceOf(CSVFormatException.class)));
	}
	
	@Test
	public void parse_parseCSVValue() throws IOException {
		CSVParser parser = CSVParser.fromString("test");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		assertThat(list, is(Arrays.asList("test")));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_parseCSVString() throws IOException {
		CSVParser parser = CSVParser.fromString("\"test\"");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		assertThat(list, is(Arrays.asList("test")));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_returnEmptyListWhenParsingWhitespace() throws IOException {
		CSVParser parser = CSVParser.fromString("   ");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		assertThat(list.size(), is(0));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_ignoreWhitespaces() throws IOException {
		CSVParser parser = CSVParser.fromString("       test     ");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();		
		assertThat(list, is(Arrays.asList("test")));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_parseCSVStringIncludingSpecialCharacter() throws IOException {
		CSVParser parser = CSVParser.fromString("\"\"\"test\"\"\", \"\n\", \"\r\"    \n");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		
		assertThat(list, is(Arrays.asList("\"test\"", "\n", "\r")));
		
		parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_parseEmptyValue() throws IOException {
		CSVParser parser = CSVParser.fromString(",\n");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is(""));
		assertThat(list.get(1), is(""));
		
		parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
	@Test
	public void parse_parseCSVRow() throws IOException {
		CSVParser parser = CSVParser.fromString("   test , 1, true,     \"\"\"ok\"\n1e+10, test2  \n");
		Either<List<String>, Exception> parseResult = parser.parse();

		assertThat(parseResult.isLeft(), is(true));
		List<String> list = parseResult.getLeft();
		assertThat(list, is(Arrays.asList("test", "1", "true", "\"ok")));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(true));
		list = parseResult.getLeft();
		assertThat(list, is(Arrays.asList("1e+10", "test2")));
		
		parseResult = parser.parse();
		assertThat(parseResult.isLeft(), is(false));
		assertThat(parseResult.getRight(), is(instanceOf(EndOfStreamException.class)));
	}
	
}
