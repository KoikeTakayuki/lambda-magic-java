package jp.lambdamagic.pipeline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.Test;
import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.Tuple2;
import jp.lambdamagic.pipeline.DataSource;
import jp.lambdamagic.pipeline.ZippedDataSource;

public class ZippedDataSourceTest {

	@Test(expected=NullArgumentException.class)
	public void ZippedDataSource_mustThrowNullArgumentExceptionWhenNullFirstSourceIsGiven() throws Exception {
		ZippedDataSource<Integer, Integer> zippedDataSource = new ZippedDataSource<>(null, () -> Optional.of(1));
		zippedDataSource.close();
	}
	
	@Test(expected=NullArgumentException.class)
	public void ZippedDataSource_mustThrowNullArgumentExceptionWhenNullSecondSourceIsGiven() throws Exception {
		ZippedDataSource<Integer, Integer> zippedDataSource = new ZippedDataSource<>(null, () -> Optional.of(1));
		zippedDataSource.close();
	}
	
	@Test
	public void readData_provideTupleZippingTwoDataSourceElement() throws Exception {
		ZippedDataSource<Integer, Integer> zippedDataSource = new ZippedDataSource<>(() -> Optional.of(1), () -> Optional.of(2));
		
		Optional<Tuple2<Integer, Integer>> data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		Tuple2<Integer, Integer> tuple = data.get();
		assertThat(tuple.getFirstValue(), is(1));
		assertThat(tuple.getSecondValue(), is(2));
		
		data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		tuple = data.get();
		assertThat(tuple.getFirstValue(), is(1));
		assertThat(tuple.getSecondValue(), is(2));
		
		zippedDataSource.close();
	}
	
	@Test
	public void readData_provideEmptyWhenEitherOneOfTheDataSourceProvideEmpty() throws Exception {
		DataSource<Integer> source1 = () -> Optional.of(1);
		DataSource<Integer> source2 = DataSource.asDataSource(2, 3, 4);
		ZippedDataSource<Integer, Integer> zippedDataSource = new ZippedDataSource<>(source1, source2);
		
		Optional<Tuple2<Integer, Integer>> data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		Tuple2<Integer, Integer> tuple = data.get();
		assertThat(tuple.getFirstValue(), is(1));
		assertThat(tuple.getSecondValue(), is(2));
		
		data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		tuple = data.get();
		assertThat(tuple.getFirstValue(), is(1));
		assertThat(tuple.getSecondValue(), is(3));
		
		data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(true));
		tuple = data.get();
		assertThat(tuple.getFirstValue(), is(1));
		assertThat(tuple.getSecondValue(), is(4));
		
		data = zippedDataSource.readData();
		assertThat(data.isPresent(), is(false));
		
		zippedDataSource.close();
	}

}
