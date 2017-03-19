package jp.lambdamagic.pipeline;

import java.util.function.Function;

@FunctionalInterface
public interface DataProcessor<I, O> extends Function<I, O>, AutoCloseable {

    O process(I input) throws Exception;

    @Override
    default O apply(I input) {
        try {
            return process(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    default void close() throws Exception {}
    
}