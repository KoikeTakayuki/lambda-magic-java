package jp.lambdamagic.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.csv.CSVDataSource;
import jp.lambdamagic.pipeline.Pipeline;

public class CSVDataSourceTest {
  
  private String TEST_DATA_FILE_PATH = "test/jp/lambdamagic/csv/test_input.csv";

  @SuppressWarnings("resource")
  @Test(expected=NullArgumentException.class)
  public void CSVDataSource_mustThrowNullArgumentExceptionWhenNullReaderIsGiven() throws IOException {
    new CSVDataSource((Reader)null);
  }
  
  @SuppressWarnings("resource")
  @Test(expected=NullArgumentException.class)
  public void CSVDataSource_mustThrowNullArgumentExceptionWhenNullFilePathIsGiven() throws IOException {
    new CSVDataSource((String)null);
  }
  
  @SuppressWarnings("resource")
  @Test(expected=NullArgumentException.class)
  public void CSVDataSource_mustThrowNullArgumentExceptionWhenNullEncodingIsGiven() throws IOException {
    new CSVDataSource("filePath", null);
  }
  
  @SuppressWarnings("resource")
  @Test(expected=FileNotFoundException.class)
  public void CSVDataSource_mustThrowFileNotFoundExceptionWhenNonExistingFilePathIsGiven() throws IOException {
    new CSVDataSource("filePath");
  }
  
  @SuppressWarnings("resource")
  @Test(expected=UnsupportedEncodingException.class)
  public void CSVDataSource_mustThrowUnsupportedEncodingExceptionWhenUnsupportedEncodingIsGiven() throws IOException {
    new CSVDataSource(TEST_DATA_FILE_PATH, "unknownEncoding");
  }
  
  @Test(expected=NullArgumentException.class)
  public void fromString_mustThrowNullArgumentExceptionWhenNullStringIsGiven() throws IOException {
    CSVDataSource.fromString(null);
  }
  
  @Test
  public void readData_readCSVRowFromGivenFile() throws IOException {
    try(CSVDataSource csvDataSource = new CSVDataSource(TEST_DATA_FILE_PATH)) {
      
      // Row1
      Optional<List<String>> maybeData = csvDataSource.readData();
      assertThat(maybeData.isPresent(), is(true));
      List<String> data = maybeData.get();
      assertThat(data, is(Arrays.asList("test1", "test2", "test3", "", "", "test4")));
      
      // Row2
      maybeData = csvDataSource.readData();
      assertThat(maybeData.isPresent(), is(true));
      data = maybeData.get();
      assertThat(data, is(Arrays.asList("1", "2", "日本語のテスト", "")));
      
      // Row3
      maybeData = csvDataSource.readData();
      assertThat(maybeData.isPresent(), is(true));
      data = maybeData.get();
      assertThat(data.size(), is(0));
      
      // Row4
      maybeData = csvDataSource.readData();
      assertThat(maybeData.isPresent(), is(true));
      data = maybeData.get();
      assertThat(data, is(Arrays.asList("test1", "\"test2\"", "test3\n", "ok")));
    }
  }
  
  @Test
  public void CSVDataSource_canBeUsedAsPipelineElement() throws Exception {
    List<Integer> list = Pipeline.from(new CSVDataSource(TEST_DATA_FILE_PATH))
        .map(l -> l.size())
        .toList();
    
    assertThat(list, is(Arrays.asList(6, 4, 0, 4)));
  }
  
}
