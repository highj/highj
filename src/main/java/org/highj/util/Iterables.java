package org.highj.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Iterables {

    public static <T> Iterable<T> emptyIterable() {
        return Iterators::emptyIterator;
    }

    public static <T> Iterable<T> valueIterable(T ... ts) {
       return () -> Iterators.valueIterator(ts);
    }

    public static <T> Iterable<T> cyclicIterable(T ... ts) {
        return () -> Iterators.cyclicIterator(ts);
    }

    @SafeVarargs
    public static <T> Iterable<T> concat(Iterable<T> ... iterables) {
        if (iterables.length == 0) {
            return emptyIterable();
        }
        return () -> new Iterator<T>() {

            private int index = 0;
            private Iterator<T> currentIterator = iterables[index++].iterator();

            @Override
            public boolean hasNext() {
                if (currentIterator.hasNext()) {
                    return true;
                } else if (index < iterables.length) {
                    currentIterator = iterables[index++].iterator();
                    return hasNext();
                } else {
                    return false;
                }
            }

            @Override
            public T next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
    }

    @SafeVarargs
    public static <A> Iterable<A> reverseIterable(A ... as) {
        return () -> new Iterator<A>() {

            private int i = as.length - 1;

            @Override
            public boolean hasNext() {
                return i >= 0;
            }

            @Override
            public A next() {
                if (! hasNext()) throw new NoSuchElementException();
                return as[i--];
            }
        };
    }
}
