package lambdamagic.pipeline.wrapper;

import java.util.Optional;

import lambdamagic.pipeline.DataSource;

public final class MergedDataSource<T> implements DataSource<T> {
	
	DataSource<T>[] sources;
	private Optional<DataSource<T>> currentDataSource;
	private int currentIndex;

	@SuppressWarnings("unchecked")
	public MergedDataSource(DataSource<T>... sources) {
		this.sources = sources;
		currentIndex = 0;
		setNextDataSource();
	}

	@Override
	public Optional<T> readData() {
		while (true) {
			if (!currentDataSource.isPresent()) {
				setNextDataSource();
	
				if (!currentDataSource.isPresent()) {
					return Optional.empty();
				}
			}
			
			DataSource<T> dataSource = currentDataSource.get();
			Optional<T> maybeData = dataSource.readData();
			
			if (maybeData.isPresent()) {
				return maybeData;
			}
			
			currentDataSource = Optional.empty();
		}
	}

	private void setNextDataSource() {
		 if (currentIndex >= sources.length)
			 currentDataSource = Optional.empty();
		 else
			 currentDataSource =  Optional.of(sources[currentIndex]);
		 
		 ++currentIndex;
	}
	
	@Override
	public void close() throws Exception {
		for (DataSource<T> s : sources) {
			s.close();
		}
	}
}
