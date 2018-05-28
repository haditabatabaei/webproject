

package io.reactivex.internal.operators.maybe;

import java.util.List;

import org.junit.Test;

import io.reactivex.*;
import io.reactivex.exceptions.*;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.observers.TestObserver;

public class MaybeFlatMapNotificationTest {

    @Test
    public void dispose() {
        TestHelper.checkDisposed(Maybe.just(1)
                .flatMap(Functions.justFunction(Maybe.just(1)),
                        Functions.justFunction(Maybe.just(1)), Functions.justCallable(Maybe.just(1))));
    }

    @Test
    public void doubleOnSubscribe() {
        TestHelper.checkDoubleOnSubscribeMaybe(new Function<Maybe<Integer>, MaybeSource<Integer>>() {
            @Override
            public MaybeSource<Integer> apply(Maybe<Integer> m) throws Exception {
                return m
                        .flatMap(Functions.justFunction(Maybe.just(1)),
                                Functions.justFunction(Maybe.just(1)), Functions.justCallable(Maybe.just(1)));
            }
        });
    }

    @Test
    public void onSuccessNull() {
        Maybe.just(1)
        .flatMap(Functions.justFunction((Maybe<Integer>)null),
                Functions.justFunction(Maybe.just(1)),
                Functions.justCallable(Maybe.just(1)))
        .test()
        .assertFailure(NullPointerException.class);
    }

    @Test
    public void onErrorNull() {
        TestObserver<Integer> to = Maybe.<Integer>error(new TestException())
        .flatMap(Functions.justFunction(Maybe.just(1)),
                Functions.justFunction((Maybe<Integer>)null),
                Functions.justCallable(Maybe.just(1)))
        .test()
        .assertFailure(CompositeException.class);

        List<Throwable> ce = TestHelper.compositeList(to.errors().get(0));

        TestHelper.assertError(ce, 0, TestException.class);
        TestHelper.assertError(ce, 1, NullPointerException.class);
    }

    @Test
    public void onCompleteNull() {
        Maybe.<Integer>empty()
        .flatMap(Functions.justFunction(Maybe.just(1)),
                Functions.justFunction(Maybe.just(1)),
                Functions.justCallable((Maybe<Integer>)null))
        .test()
        .assertFailure(NullPointerException.class);
    }

    @Test
    public void onSuccessEmpty() {
        Maybe.just(1)
        .flatMap(Functions.justFunction(Maybe.<Integer>empty()),
                Functions.justFunction(Maybe.just(1)),
                Functions.justCallable(Maybe.just(1)))
        .test()
        .assertResult();
    }

    @Test
    public void onSuccessError() {
        Maybe.just(1)
        .flatMap(Functions.justFunction(Maybe.<Integer>error(new TestException())),
                Functions.justFunction((Maybe<Integer>)null),
                Functions.justCallable(Maybe.just(1)))
        .test()
        .assertFailure(TestException.class);
    }
}
