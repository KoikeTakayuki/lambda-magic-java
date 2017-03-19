package jp.lambdamagic.pipeline;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import jp.lambdamagic.NullArgumentException;

public class RepetitiveDataSource<T> implements DataSource<T> {
    
    private DataSource<T> wrapped;
    private List<Optional<T>> cache;
    private boolean readAll;
    private int currentIndex = 0;

    public RepetitiveDataSource(DataSource<T> wrapped) {
        if (wrapped == null) {
            throw new NullArgumentException("wrapped");
        }
        
        this.wrapped = wrapped;
        this.cache = new LinkedList<>();
    }

    @Override
    public Optional<T> readData() {
        if (readAll) {
            return getCachedData();
        }
        
        Optional<T> maybeData = wrapped.readData();
        
        if (maybeData.isPresent()) {
            cacheData(maybeData);
            return maybeData;
        }
        
        readAll = true;
        return getCachedData();
    }
    
    private void cacheData(Optional<T> data) {
        cache.add(data);
    }

    private Optional<T> getCachedData() {
        int size = cache.size();
        
        if (size == 0) {
            return Optional.empty();
        }
        
        if (currentIndex >= size) {
            currentIndex = 0;
        }
        
        return cache.get(currentIndex++);
    }

    @Override
    public void close() throws Exception {
        wrapped.close();
    }

}
