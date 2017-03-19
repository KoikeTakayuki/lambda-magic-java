package jp.lambdamagic.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jp.lambdamagic.InvalidArgumentException;
import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.Queryable;
import jp.lambdamagic.data.Settable;

public class DependencyInjector implements Queryable<Class<?>, Object>, Settable<Class<?>, Object> {

    private static DependencyInjector instance;
    
    private Map<Class<?>, Object> dependencies;
    
    public static DependencyInjector getInstance() {
        if (instance == null) {
            instance = new DependencyInjector();
        }
        
        return instance;
    }
    
    private DependencyInjector() {
        this.dependencies = new HashMap<Class<?>, Object>();
    }
    
    @Override
    public Optional<Object> get(Class<?> abstractClass) {
        return Optional.ofNullable(dependencies.get(abstractClass));
    }
    
    @Override
    public void set(Class<?> abstractClass, Object concreteObject) {
        if (abstractClass == null) {
            throw new NullArgumentException("abstractClass");
        }
        
        if (concreteObject == null) {
            throw new NullArgumentException("concreteObject");
        }
        
        if (!abstractClass.isInstance(concreteObject)) {
            throw new InvalidArgumentException("concreteObject", "concreateObject is not instance of given class");
        }
        
        dependencies.put(abstractClass, concreteObject);
    }
    
}
