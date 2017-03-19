package jp.lambdamagic.event;

import java.util.function.Consumer;

@FunctionalInterface
public interface EventListener<E extends Event> extends java.util.EventListener, Consumer<E> {
    
    void onEvent(E e);
    
    default void accept(E e) {
        onEvent(e);
    }
    
}
