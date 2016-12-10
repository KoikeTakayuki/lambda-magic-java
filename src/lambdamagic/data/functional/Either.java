package lambdamagic.data.functional;

import java.util.function.Function;

import lambdamagic.NullArgumentException;

public abstract class Either<L, R> {

	protected L left;
	protected R right;

	public abstract boolean isLeft();

	public boolean isRight() {
		return !isLeft();
	}

	public L getLeft() {
		if (isRight())
			throw new UnsupportedOperationException();
			
		return left;
	}

	public R getRight() {
		if (isLeft())
			throw new UnsupportedOperationException();

		return right;
	}

	@SuppressWarnings("unchecked")
	public <T> Either<T, R> applyToLeft(Function<L, T> f) {
		if (isLeft()) {
			return left(f.apply(left));
		}
		
		return (Either<T, R>)this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Either<L, T> applyToRight(Function<R, T> f) {
		if (isRight()) {
			return right(f.apply(right));
		}

		return (Either<L, T>)this;
	}

    public static <L, R> Either<L, R> right(R right) {
    	if (right == null)
    		throw new NullArgumentException("right");

        return new Right<L, R>(right);
    }
    
    public static <L, R> Either<L, R> left(L left) {
    	if (left == null)
    		throw new NullArgumentException("left");
   
        return new Left<L, R>(left);
    }
    

    final static class Left<L, R> extends Either<L, R> {

    	private Left(L value) {
    		this.left = value;
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
    
    final static class Right<L, R> extends Either<L, R> {
    		
    	private Right(R value) {
    		this.right = value;
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


}