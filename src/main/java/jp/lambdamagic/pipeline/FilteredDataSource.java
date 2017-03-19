package jp.lambdamagic.pipeline;

import java.util.Optional;
import java.util.function.Predicate;

import jp.lambdamagic.NullArgumentException;

public final class FilteredDataSource<T> implements DataSource<T> {

  private DataSource<T> wrapped;
  private Predicate<T> predicate;

  public FilteredDataSource(DataSource<T> wrapped, Predicate<T> predicate) {
    if (wrapped == null) {
      throw new NullArgumentException("wrapped");
    }
    
    if (predicate == null) {
      throw new NullArgumentException("predicate");
    }    
    
    this.wrapped = wrapped;
    this.predicate = predicate;
  }

  @Override
  public Optional<T> readData() {
    while (true) {
      Optional<T> maybeData = wrapped.readData();

      if (!maybeData.isPresent()) {
        return Optional.empty();
      }
      
      T data = maybeData.get();

      if (predicate.test(data)) {
        return maybeData;
      }
    }
  }
  
  @Override
  public void close() throws Exception {
    wrapped.close();
  }
  
}
