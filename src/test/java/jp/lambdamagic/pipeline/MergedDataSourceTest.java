package jp.lambdamagic.pipeline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataSource;
import jp.lambdamagic.pipeline.MergedDataSource;

public class MergedDataSourceTest {
  
  @Test(expected=NullArgumentException.class)
  public void MergedDataSource_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
    MergedDataSource<?> mergedDataSource = new MergedDataSource<>((DataSource<String>[])null);
    mergedDataSource.close();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void readData_provideWrappedDataSourceElementInSequentialOrder() throws Exception {
    MergedDataSource<Integer> mergedDataSource = new MergedDataSource<>(DataSource.asDataSource(1, 2), DataSource.asDataSource(3, 4));
    
    Optional<Integer> data = mergedDataSource.readData();
    
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(1));
    
    data = mergedDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(2));
    
    data = mergedDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(3));
    
    data = mergedDataSource.readData();
    assertThat(data.isPresent(), is(true));
    assertThat(data.get(), is(4));
    
    data = mergedDataSource.readData();
    assertThat(data.isPresent(), is(false));
    
    mergedDataSource.close();
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void readData_provideEmptyWhenWrappedDataSourceProvideEmpty() throws Exception {
    MergedDataSource<?> mergedDataSource = new MergedDataSource<>(() -> Optional.empty());
    Optional<?> readData = mergedDataSource.readData();
    assertThat(readData.isPresent(), is(false));
    mergedDataSource.close();
  }

}
