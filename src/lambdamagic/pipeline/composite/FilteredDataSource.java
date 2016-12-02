package lambdamagic.pipeline.composite;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import lambdamagic.pipeline.DataSource;

public class FilteredDataSource<T> implements DataSource<T> {

	private DataSource<T> wrapped;
	private Predicate<T> predicate;

	public FilteredDataSource(DataSource<T> wrapped, Predicate<T> predicate) {
		this.wrapped = wrapped;
		this.predicate = predicate;
	}

	@Override
	public Optional<T> readData() {
		
		while (true) {
			Optional<T> maybeData = wrapped.readData();

			if (!maybeData.isPresent())
				return Optional.empty();

			T data = maybeData.get();

			if (predicate.test(data))
				return maybeData;
		}
	}
	
	@Override
	public void close() throws IOException {
		wrapped.close();
	}
}
