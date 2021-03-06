

package io.reactivex.tck;

import org.reactivestreams.Publisher;
import org.testng.annotations.Test;

import io.reactivex.*;

@Test
public class ConcatWithMaybeEmptyTckTest extends BaseTck<Integer> {

    @Override
    public Publisher<Integer> createPublisher(long elements) {
        return
                Flowable.range(1, (int)elements)
                .concatWith(Maybe.<Integer>empty())
            ;
    }
}
