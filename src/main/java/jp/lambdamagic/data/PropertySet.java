package jp.lambdamagic.data;

import java.util.Set;

public interface PropertySet<T> extends Queryable<String, T>, Settable<String, T> {
	Set<String> propertyNames();
}
