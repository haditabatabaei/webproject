

package io.reactivex.tck;

import org.reactivestreams.Publisher;
import org.testng.annotations.Test;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.Functions;

@Test
public class SwitchMapTckTest extends BaseTck<Long> {

    @Override
    public Publisher<Long> createPublisher(long elements) {
        return
            Flowable.just(1).switchMap(Functions.justFunction(
                    Flowable.fromIterable(iterate(elements)))
            )
        ;
    }
}
