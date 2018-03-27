

package io.reactivex.tck;

import org.reactivestreams.Publisher;
import org.testng.annotations.Test;

import io.reactivex.Flowable;

@Test
public class LastTckTest extends BaseTck<Integer> {

    @Override
    public Publisher<Integer> createPublisher(final long elements) {
        return
                Flowable.range(1, 10).lastElement().toFlowable()
            ;
    }

    @Override
    public long maxElementsFromPublisher() {
        return 1;
    }
}
