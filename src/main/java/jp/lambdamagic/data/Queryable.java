package jp.lambdamagic.data;

import java.util.Optional;

public interface Queryable<T1, T2> {
    Optional<T2> get(T1 propertyName);
}
