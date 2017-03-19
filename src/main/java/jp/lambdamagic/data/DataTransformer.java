package jp.lambdamagic.data;

import java.util.function.Function;

@FunctionalInterface
public interface DataTransformer<IN, OUT> extends Function<IN, OUT> {

    OUT transform(IN input);

    @Override
    default OUT apply(IN input) {
        return transform(input);
    }
    
}