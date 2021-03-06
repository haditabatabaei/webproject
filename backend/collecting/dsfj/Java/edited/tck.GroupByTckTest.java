

package io.reactivex.tck;

import org.reactivestreams.Publisher;
import org.testng.annotations.Test;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;

@Test
public class GroupByTckTest extends BaseTck<Integer> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Publisher<Integer> createPublisher(long elements) {
        return
                Flowable.range(0, (int)elements).groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer v) throws Exception {
                        return v & 1;
                    }
                })
                .flatMap((Function)Functions.identity())
        ;
    }
}
