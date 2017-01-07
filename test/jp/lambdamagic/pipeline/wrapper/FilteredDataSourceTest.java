package jp.lambdamagic.pipeline.wrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataSource;
import jp.lambdamagic.pipeline.wrapper.FilteredDataSource;

public class FilteredDataSourceTest {

	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void FilteredDataSource_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() {
		new FilteredDataSource<Integer>(null, number -> (number % 2 == 1));
	}
	
	@SuppressWarnings("resource")
	@Test(expected=NullArgumentException.class)
	public void FilteredDataSource_mustThrowNullArgumentExceptionWhenNullPredicateIsGiven() {
		new FilteredDataSource<Integer>(DataSource.asDataSource(1, 2, 3, 4, 5), null);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void readData_provideWrappedDataSourceElementFilteredByPredicate() {
		FilteredDataSource<Integer> filteredDataSource = new FilteredDataSource<>(DataSource.asDataSource(1, 2, 3, 4, 5), number -> (number % 2 == 1));
		
		Optional<Integer> data = filteredDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(1));
		
		data = filteredDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(3));
		
		data = filteredDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(5));
		
		data = filteredDataSource.readData();
		assertThat(data.isPresent(), is(false));
	}
	
	@Test
	public void readData_provideEmptyWhenWrappedDataSourceProvideEmpty() throws Exception {
		FilteredDataSource<?> filteredDataSource = new FilteredDataSource<>(() -> Optional.empty(), x -> true);
		Optional<?> data = filteredDataSource.readData();
		assertThat(data.isPresent(), is(false));
		filteredDataSource.close();
	}

}