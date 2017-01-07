package jp.lambdamagic.settings;

import java.util.Set;

import jp.lambdamagic.data.Queryable;
import jp.lambdamagic.data.Settable;

public interface PropertySet<T> extends Queryable<String, T>, Settable<String, T> {

	Set<String> propertyNames();
	boolean save();
}
