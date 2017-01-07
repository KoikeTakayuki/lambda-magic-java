package jp.lambdamagic.pipeline.wrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.InvalidArgumentException;
import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.wrapper.TrimmedDataSource;

public class TrimmedDataSourceTest {

	@Test(expected=NullArgumentException.class)
	public void TrimmedDataSource_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
		TrimmedDataSource<Object> trimmedDataSource = new TrimmedDataSource<>(null, 10);
		trimmedDataSource.close();
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void TrimmedDataSource_mustThrowInvalidArgumentExceptionWhenNegativeTrimCountIsGiven() throws Exception {
		TrimmedDataSource<Integer> trimmedDataSource = new TrimmedDataSource<>(() -> Optional.of(1), -1);
		trimmedDataSource.close();
	}
	
	@Test
	public void readData_provideWrappedDataSourceElementOnlySpecifiedCount() throws Exception {
		TrimmedDataSource<Integer> trimmedDataSource = new TrimmedDataSource<>(() -> Optional.of(1), 3);
		
		Optional<Integer> data = trimmedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(1));
		
		data = trimmedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(1));
		
		data = trimmedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(1));
		
		data = trimmedDataSource.readData();
		assertThat(data.isPresent(), is(false));
		
		trimmedDataSource.close();
	}
	
	@Test
	public void readData_provideEmptyWhenWrappedDataSourceProvideEmpty() throws Exception {
		TrimmedDataSource<?> trimmedDataSource = new TrimmedDataSource<>(() -> Optional.empty(), 10);
		Optional<?> readData = trimmedDataSource.readData();
		assertThat(readData.isPresent(), is(false));
		trimmedDataSource.close();
	}
}
