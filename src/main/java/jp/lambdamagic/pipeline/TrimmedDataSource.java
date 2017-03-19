package jp.lambdamagic.pipeline;

import java.util.Optional;

import jp.lambdamagic.InvalidArgumentException;
import jp.lambdamagic.NullArgumentException;

public final class TrimmedDataSource<T> implements DataSource<T> {

  private DataSource<T> wrapped;
  private int trimCount;
  private int readCount;

  public TrimmedDataSource(DataSource<T> wrapped, int trimCount) {
    if (wrapped == null) {
      throw new NullArgumentException("wrapped");
    }
    
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
    maybeData.ifPresent(data -> ++readCount);

    return maybeData;
  }

  @Override
  public void close() throws Exception {
    wrapped.close();
  }

}
