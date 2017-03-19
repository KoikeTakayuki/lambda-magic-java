package jp.lambdamagic.data.functional;

import java.util.function.Function;

import jp.lambdamagic.NullArgumentException;

public abstract class Either<L, R> {
    
    private final static class Left<L, R> extends Either<L, R> {

        private Left(L leftValue) {
            if (leftValue == null) {
                throw new NullArgumentException("leftValue");
            }
            
            this.left = leftValue;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }
    }
    
    private final static class Right<L, R> extends Either<L, R> {
            
        private Right(R rightValue) {
            if (rightValue == null) {
                throw new NullArgumentException("rightValue");
            }
            
            this.right = rightValue;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }
    }
    
    public static <L, R> Either<L, R> right(R rightValue) {
        return new Right<L, R>(rightValue);
    }
    
    public static <L, R> Either<L, R> left(L leftValue) {
        return new Left<L, R>(leftValue);
    }

    protected L left;
    protected R right;

    public abstract boolean isLeft();

    public boolean isRight() {
        return !isLeft();
    }

    public L getLeft() {
        if (isRight()) {
            throw new UnsupportedOperationException("try to get left element, but actual instance is right");
        }
            
        return left;
    }

    public R getRight() {
        if (isLeft()) {
            throw new UnsupportedOperationException("try to get right element, but actual instance is left");
        }

        return right;
    }

    @SuppressWarnings("unchecked")
    public <T> Either<T, R> applyToLeft(Function<L, T> function) {
        if (function == null) {
            throw new NullArgumentException("function");
        }
        
        if (isLeft()) {
            return left(function.apply(left));
        }
        
        return (Either<T, R>)this;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Either<L, T> applyToRight(Function<R, T> function) {
        if (function == null) {
            throw new NullArgumentException("function");
        }
        
        if (isRight()) {
            return right(function.apply(right));
        }
        
        return (Either<L, T>)this;
    }
    
}