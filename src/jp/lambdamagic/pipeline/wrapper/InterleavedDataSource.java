package jp.lambdamagic.pipeline.wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;
import jp.lambdamagic.pipeline.DataSource;

public final class InterleavedDataSource<T> implements DataSource<T> {

	private Iterable<DataSource<T>> sources;
	private Iterator<DataSource<T>> dataSourceIterator;
	private LinkedList<DataSource<T>> dataSourceList;
	private int currentIndex;
	
	public InterleavedDataSource(Iterable<DataSource<T>> sources) {
		if (sources == null) {
			throw new NullArgumentException("sources");
		}
		
		this.sources = sources;
		this.dataSourceIterator = sources.iterator();
		this.dataSourceList = new LinkedList<>();
		this.currentIndex = 0;
	}

	@SuppressWarnings("unchecked")
	public InterleavedDataSource(DataSource<T>... sources) {
		this(Iterables.asIterable(sources));
	}

	@Override
	public Optional<T> readData() {
		while (true) {
			Optional<DataSource<T>> maybeDataSource = getDataSource();
			
			if (!maybeDataSource.isPresent()) {
				return Optional.empty();
			}
			
			DataSource<T> dataSource = maybeDataSource.get();
 
			Optional<T> data = dataSource.readData();
			
			if (data.isPresent()) {
				return data;
			}
			
			unregisterDataSource(dataSource);
		}
	}

	@Override
	public void close() throws Exception {
		for (DataSource<T> s : sources) {
			s.close();
		}
	}
	
	private void registerDataSource(DataSource<T> dataSource) {
		dataSourceList.add(dataSource);
	}
	
	private void unregisterDataSource(DataSource<T> dataSource) {
		dataSourceList.remove(dataSource);
		int size = dataSourceList.size();
		
		// adjust index
		if (currentIndex == size) {
			currentIndex = size - 1;
		} else if (currentIndex > size) {
			currentIndex = 0;
		}
	}
	
	private Optional<DataSource<T>> getDataSource() {
		if (dataSourceIterator.hasNext()) {
			DataSource<T> dataSource = dataSourceIterator.next();
			registerDataSource(dataSource);
			return Optional.of(dataSource);
		}
		
		if (dataSourceList.size() == 0) {
			return Optional.empty();
		}
		
		if (currentIndex >= dataSourceList.size()) {
			currentIndex = 0;
		}
		
		DataSource<T> dataSource = dataSourceList.get(currentIndex);
		step();
		return Optional.of(dataSource);
	}
	
	private void step() {
		++currentIndex;
		
		if (currentIndex >= dataSourceList.size()) {
			currentIndex = 0;
		}
	}
	
}
