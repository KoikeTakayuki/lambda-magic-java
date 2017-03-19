package jp.lambdamagic.data.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.data.functional.Either;

public class EitherTest {

    @Test(expected=NullArgumentException.class)
    public void left_mustThrowNullArgumentExceptionWhenNullLeftValueIsGiven() {
        Either.left(null);
    }
    
    @Test
    public void isLeft_returnsWheterEitherObjectHasLeftValueOrNot() {
        Either<Integer, Exception> left = Either.left(1);
        assertThat(left.isLeft(), is(true));

        Either<Integer, Exception> right = Either.right(new Exception());
        assertThat(right.isLeft(), is(false));
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void getLeft_mustThrowUnsupportedOperationExceptionWhenRightValueIsPresent() {
        Either<Integer, Exception> right = Either.right(new Exception());
        right.getLeft();
    }
    
    @Test
    public void getLeft_returnLeftValue() {
        Either<Integer, Exception> left = Either.left(1);
        assertThat(left.getLeft(), is(1));
    }
    
    @Test(expected=NullArgumentException.class)
    public void applyToLeft_mustThrowNullArgumentExceptionWhenNullFunctionIsGiven() {
        Either.left(1).applyToLeft(null);
    }
    
    @Test
    public void applyToLeft_mapLeftValueToAnother() {
        Either<Integer, Exception> left = Either.left(1);
        left = left.applyToLeft(e -> e + 1);
        
        assertThat(left.isLeft(), is(true));
        assertThat(left.getLeft(), is(2));
    }
    
    @Test
    public void applyToLeft_doNothingForRightValue() {
        Either<Integer, Integer> right = Either.right(1);
        right = right.applyToLeft(e -> e + 1);
        
        assertThat(right.isRight(), is(true));
        assertThat(right.getRight(), is(1));
    }
    
    @Test(expected=NullArgumentException.class)
    public void right_mustThrowNullArgumentExceptionWhenNullRightValueIsGiven() {
        Either.right(null);
    }
    
    @Test
    public void isRight_returnsWheterEitherObjectHasRightValueOrNot() {
        Either<Integer, Exception> left = Either.left(1);
        assertThat(left.isRight(), is(false));

        Either<Integer, Exception> right = Either.right(new Exception());
        assertThat(right.isRight(), is(true));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void getRight_mustThrowUnsupportedOperationExceptionWhenLeftValueIsPresent() {
        Either<Integer, Exception> left = Either.left(1);
        left.getRight();
    }

    @Test
    public void getRight_returnRightValue() {
        Either<Integer, String> right = Either.right("test");
        assertThat(right.getRight(), is("test"));
    }

    @Test(expected=NullArgumentException.class)
    public void applyToRight_mustThrowNullArgumentExceptionWhenNullFunctionIsGiven() {
        Either.right(1).applyToRight(null);
    }
    
    @Test
    public void applyToRight_mapRightValueToAnother() {
        Either<Integer, Integer> right = Either.right(1);
        right = right.applyToRight(e -> e + 1);
        
        assertThat(right.isRight(), is(true));
        assertThat(right.getRight(), is(2));
    }
    
    @Test
    public void applyToRight_doNothingForLeftValue() {
        Either<Integer, Integer> left = Either.left(1);
        left = left.applyToRight(e -> e + 1);
        
        assertThat(left.isRight(), is(false));
        assertThat(left.getLeft(), is(1));
    }
    
}
