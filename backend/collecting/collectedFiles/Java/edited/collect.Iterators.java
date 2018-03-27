

package org.elasticsearch.common.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Iterators {
    public static <T> Iterator<T> concat(Iterator<? extends T>... iterators) {
        if (iterators == null) {
            throw new NullPointerException("iterators");
        }

                return new ConcatenatedIterator<T>(iterators);
    }

    static class ConcatenatedIterator<T> implements Iterator<T> {
        private final Iterator<? extends T>[] iterators;
        private int index = 0;

        ConcatenatedIterator(Iterator<? extends T>... iterators) {
            if (iterators == null) {
                throw new NullPointerException("iterators");
            }
            for (int i = 0; i < iterators.length; i++) {
                if (iterators[i] == null) {
                    throw new NullPointerException("iterators[" + i  + "]");
                }
            }
            this.iterators = iterators;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = false;
            while (index < iterators.length && !(hasNext = iterators[index].hasNext())) {
                index++;
            }

            return hasNext;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iterators[index].next();
        }
    }
}
