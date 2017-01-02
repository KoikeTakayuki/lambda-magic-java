package lambdamagic.pipeline.wrapper;

import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.data.Tuple2;
import lambdamagic.pipeline.DataSource;

public final class ZippedDataSource<T1, T2> implements DataSource<Tuple2<T1, T2>> {
	
	private DataSource<T1> source1;
	private DataSource<T2> source2;

	public ZippedDataSource(DataSource<T1> source1, DataSource<T2> source2) {
		if (source1 == null) {
			throw new NullArgumentException("source1");
		}
		
		if (source2 == null) {
			throw new NullArgumentException("source2");
		}
		
		this.source1 = source1;
		this.source2 = source2;
	}

	@Override
	public Optional<Tuple2<T1, T2>> readData() {
		Optional<T1> maybeData1 = source1.readData();
		
		if (!maybeData1.isPresent()) {
			return Optional.empty();
		}
		
		Optional<T2> maybeData2 = source2.readData();
		
		if (!maybeData2.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new Tuple2<T1, T2>(maybeData1.get(), maybeData2.get()));
	}

	@Override
	public void close() throws Exception {
		source1.close();
		source2.close();
	}
	
}