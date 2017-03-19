package jp.lambdamagic.pipeline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataSource;
import jp.lambdamagic.pipeline.RepetitiveDataSource;

public class RepetitiveDataSourceTest {

  @Test(expected=NullArgumentException.class)
  public void RepetitiveDataSource_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
    RepetitiveDataSource<?> repetitiveDataSource = new RepetitiveDataSource<>(null);
    repetitiveDataSource.close();
  }
  
  @Test
  public void readData_provideWrappedDataSourceElementRepeatedly() throws Exception {
    DataSource<Integer> source = DataSource.asDataSource(1, 2, 3);
    RepetitiveDataSource<Integer> repetitiveDataSource = new RepetitiveDataSource<>(source);
    
    Optional<Integer> data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(1));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(2));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(3));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(1));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(2));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(3));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(1));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(2));
    
    data = repetitiveDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(3));
    
    repetitiveDataSource.close();
  }
  
  @Test
  public void readData_provideEmptyWhenWrappedDataSourceProvideEmpty() throws Exception {
    RepetitiveDataSource<?> repetitiveDataSource = new RepetitiveDataSource<>(() -> Optional.empty());
    Optional<?> readData = repetitiveDataSource.readData();
    assertThat(readData.isPresent(), is(false));
    repetitiveDataSource.close();
  }

}
