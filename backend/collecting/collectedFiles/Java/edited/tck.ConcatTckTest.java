

package io.reactivex.tck;

import org.reactivestreams.Publisher;
import org.testng.annotations.Test;

import io.reactivex.Flowable;

@Test
public class ConcatTckTest extends BaseTck<Long> {

    @Override
    public Publisher<Long> createPublisher(long elements) {
        return
                Flowable.concat(
                    Flowable.fromIterable(iterate(elements / 2)),
                    Flowable.fromIterable(iterate(elements - elements / 2))
                )
            ;
    }
}