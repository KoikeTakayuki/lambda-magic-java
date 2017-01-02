package lambdamagic.pipeline.wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lambdamagic.NullArgumentException;
import lambdamagic.pipeline.DataSource;

public final class InterleavedDataSource<T> implements DataSource<T> {

	private List<DataSource<T>> sources;
	private int currentIndex;

	@SuppressWarnings("unchecked")
	public InterleavedDataSource(DataSource<T>... sources) {
		if (sources == null) {
			throw new NullArgumentException("sources");
		}
		
		this.sources = new ArrayList<DataSource<T>>(Arrays.asList(sources));
		currentIndex = 0;
	}

	@Override
	public Optional<T> readData() {

		while (true) {

			Optional<DataSource<T>> maybeSource = getDataSource();

			if (!maybeSource.isPresent())
				return Optional.empty();

			DataSource<T> dataSource = maybeSource.get();

			Optional<T> maybeData = dataSource.readData();
	
			if (maybeData.isPresent()) {
				step();
				return maybeData;
			}

			try {
				dataSource.close();
			} catch(Exception e) {
				e.printStackTrace();
			}

			sources.remove(dataSource);

			if (currentIndex >= sources.size())
				currentIndex = 0;
		}
	}
	
	private Optional<DataSource<T>> getDataSource() {
		if (sources.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(sources.get(currentIndex));
	}

	private void step() {
		++currentIndex;

		if (currentIndex >= sources.size()) {
			currentIndex = 0;
		}
	}
	
}
