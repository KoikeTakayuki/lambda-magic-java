package lambdamagic.pipeline.wrapper;

import java.util.Optional;

import lambdamagic.InvalidArgumentException;
import lambdamagic.pipeline.DataSource;

public final class TrimmedDataSource<T> implements DataSource<T> {

	private DataSource<T> wrapped;
	private int trimCount;
	private int readCount;

	public TrimmedDataSource(DataSource<T> wrapped, int trimCount) {
		if (trimCount < 0) {
			throw new InvalidArgumentException("trimCount", "trimCount should be positive");
		}

		this.wrapped = wrapped;
		this.trimCount = trimCount;
		readCount = 0;
	}

	@Override
	public Optional<T> readData() {
		if (trimCount <= readCount) {
			return Optional.empty();
		}

		Optional<T> maybeData = wrapped.readData();

		if (maybeData.isPresent()) {
			++readCount;
			return maybeData;
		}

		return Optional.empty();
	}

	@Override
	public void close() throws Exception {
		wrapped.close();
	}

}
