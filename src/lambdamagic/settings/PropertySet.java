package lambdamagic.settings;

import java.util.Set;
import lambdamagic.data.Queryable;
import lambdamagic.data.Settable;

public interface PropertySet<T> extends Queryable<String, T>, Settable<String, T> {
	
	Set<String> propertyNames();
	boolean save();
}
