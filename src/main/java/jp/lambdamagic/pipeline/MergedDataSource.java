package jp.lambdamagic.pipeline;

import java.util.Iterator;
import java.util.Optional;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;

public final class MergedDataSource<T> implements DataSource<T> {
    
    private Iterable<DataSource<T>> sources;
    private Iterator<DataSource<T>> dataSourceIterator;
    private DataSource<T> currentDataSource;
    
    public MergedDataSource(Iterable<DataSource<T>> sources) {
        if (sources == null) {
            throw new NullArgumentException("sources");
        }

        this.sources = sources;
        this.dataSourceIterator = sources.iterator();

        if (!dataSourceIterator.hasNext()) {
            throw new IllegalStateException("Given iterable has no element");
        }

        setNextDataSource();
    }

    @SuppressWarnings("unchecked")
    public MergedDataSource(DataSource<T>... sources) {
        this(Iterables.asIterable(sources));
    }
    
    @Override
    public Optional<T> readData() {
        while (true) {
            Optional<T> data = currentDataSource.readData();
            
            if (data.isPresent()) {
                return data;
            }
            
            if (!dataSourceIterator.hasNext()) {
                return Optional.empty();
            }
    
            setNextDataSource();
        }
    }
    
    private void setNextDataSource() {
        DataSource<T> next = dataSourceIterator.next();
        
        if (next == null) {
            throw new IllegalStateException("One of the given data source is null");
        }
        
        currentDataSource = next;
    }
    
    @Override
    public void close() throws Exception {
        for (DataSource<T> s : sources) {
            s.close();
        }
    }
    
}
