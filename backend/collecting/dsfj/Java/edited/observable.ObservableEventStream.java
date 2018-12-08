

package io.reactivex.observable;

import java.util.*;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public final class ObservableEventStream {
    private ObservableEventStream() {
        throw new IllegalStateException("No instances!");
    }
    public static Observable<Event> getEventStream(final String type, final int numInstances) {

        return Observable.<Event>generate(new EventConsumer(numInstances, type)).subscribeOn(Schedulers.newThread());
    }

    public static Event randomEvent(String type, int numInstances) {
        Map<String, Object> values = new LinkedHashMap<String, Object>();
        values.put("count200", randomIntFrom0to(4000));
        values.put("count4xx", randomIntFrom0to(300));
        values.put("count5xx", randomIntFrom0to(500));
        return new Event(type, "instance_" + randomIntFrom0to(numInstances), values);
    }

    private static int randomIntFrom0to(int max) {
                long x = System.nanoTime();
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        return Math.abs((int) x % max);
    }

    static final class EventConsumer implements Consumer<Emitter<Event>> {
        private final int numInstances;
        private final String type;

        EventConsumer(int numInstances, String type) {
            this.numInstances = numInstances;
            this.type = type;
        }

        @Override
        public void accept(Emitter<Event> s) {
            s.onNext(randomEvent(type, numInstances));
            try {
                                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                s.onError(e);
            }
        }
    }

    public static class Event {
        public final String type;
        public final String instanceId;
        public final Map<String, Object> values;

        
        public Event(String type, String instanceId, Map<String, Object> values) {
            this.type = type;
            this.instanceId = instanceId;
            this.values = Collections.unmodifiableMap(values);
        }
    }
}