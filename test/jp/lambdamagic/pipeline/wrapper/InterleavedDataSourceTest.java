package jp.lambdamagic.pipeline.wrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.pipeline.DataSource;

public class InterleavedDataSourceTest {

	@Test(expected=NullArgumentException.class)
	public void InterleavedDataSource_mustThrowNullArgumentExceptionWhenNullDataSourceIsGiven() throws Exception {
		InterleavedDataSource<String> interleavedDataSource = new InterleavedDataSource<String>((DataSource<String>[])null);
		interleavedDataSource.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void readData_provideDataSourceElementInParallelOrder() throws Exception {
		DataSource<Integer> source1 = DataSource.asDataSource(1, 2, 3, 4);
		DataSource<Integer> source2 = DataSource.asDataSource(5, 6);
		DataSource<Integer> source3 = DataSource.asDataSource(7, 8, 9);
		
		InterleavedDataSource<Integer> interleavedDataSource = new InterleavedDataSource<>(source1, source2, source3);
		
		Optional<Integer> data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(1));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(5));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(7));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(2));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(6));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(8));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(3));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(9));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		assertThat(data.get(), is(4));
		
		data = interleavedDataSource.readData();
		assertThat(data.isPresent(), is(false));
		
		interleavedDataSource.close();
	}

}
