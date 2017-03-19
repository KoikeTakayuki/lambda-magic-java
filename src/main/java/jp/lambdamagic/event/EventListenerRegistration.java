package jp.lambdamagic.event;

@FunctionalInterface
public interface EventListenerRegistration {
    void remove();
}
