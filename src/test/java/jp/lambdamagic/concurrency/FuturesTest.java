package jp.lambdamagic.concurrency;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.concurrency.Futures;

public class FuturesTest {
    
    @Test(expected=NullArgumentException.class)
    public void runAsync_mustThrowNullArgumentExceptionWhenNullArgumentIsGiven() {
        Futures.runAsync((Supplier<?>)null);
    }
    
    @Test
    public void runAsync_createNewCompletableFuture() {
        Futures.runAsync(() -> 100).thenAccept(number -> {
            assertThat(number, is(100));
        });
    }

    @Test(expected=NullArgumentException.class)
    public void lift_mustThrowNullArgumentExceptionWhenNullFunctionIsGiven() {
        Futures.lift(null);
    }

    @Test
    public void wrap_wrapGivenValueIntoCompletableFuture() {
        Futures.wrap(1).thenAcceptAsync(number -> {
            assertThat(number, is(1));
        }).join();
    }
    
    @Test(expected=NullArgumentException.class)
    public void wrap_mustThrowNullArgumentExceptionWhenNullValueIsGiven() {
        Futures.wrap(null);
    }

    @Test
    public void lift_liftUpGivenFunctionToAnotherFunctionWhichIsApplicableForCompletableFuture() {
        Function<CompletableFuture<String>, CompletableFuture<Integer>> function = Futures.lift((String string) -> string.length());
        
        function.apply(Futures.runAsync(() -> "test")).thenAccept(length -> {
            assertThat(length, is(4));
        });
    }

    @Test(expected=NullArgumentException.class)
    public void all_mustThrowNullArgumentExceptionWhenNullTasksIsGiven() {
        Futures.all(null);
    }
    
    @Test(timeout=5000)
    public void all_returnAllTheResultOfGivenTasks() {
        Futures.all(
            Arrays.asList(
                Futures.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }),
                Futures.runAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 2;
                }),
                Futures.runAsync(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 3;
                })
            )
        ).thenAcceptAsync(result -> {
            int counter = 1;
            System.out.println(result);
            
            for (int e : result) {
                assertThat(e, is(counter));
                ++counter;
            }
        }).join();
    }

    @Test(expected=NullArgumentException.class)
    public void any_mustThrowNullArgumentExceptionWhenNullTasksIsGiven() {
        Futures.any(null);
    }
    
    @Test(timeout=1000)
    public void any_returnOneOfTheResultOfGivenTasks() {
        Futures.any(
            Arrays.asList(
                Futures.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }),
                Futures.runAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 2;
                }),
                Futures.runAsync(() -> 3)
            )
        ).thenAccept(result -> {
            System.out.println(result);
            assertThat(result, is(3));
        }).join();
    }

    @Test(expected=NullArgumentException.class)
    public void join_mustThrowNullArgumentExceptionWhenNullTasks1IsGiven() {
        Futures.join(null, Futures.runAsync(() -> 1));
    }

    @Test(expected=NullArgumentException.class)
    public void join_mustThrowNullArgumentExceptionWhenNullTasks2IsGiven() {
        Futures.join(Futures.runAsync(() -> 1), null);
    }
    
    @Test
    public void join_joinTwoTasksIntoOneTuple() {
        Futures.join(Futures.runAsync(() -> 1), Futures.runAsync(() -> 2)).thenAccept(tuple -> {
            assertThat(tuple.getFirstValue(), is(1));
            assertThat(tuple.getSecondValue(), is(2));
        });
    }
    
}
