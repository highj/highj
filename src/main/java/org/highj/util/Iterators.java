package org.highj.util;

import org.highj.data.tuple.T2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public enum Iterators {
    ;

    @SuppressWarnings("rawtypes")
    private final static Iterator EMPTY_ITERATOR = new Iterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }
    };

    @SuppressWarnings("unchecked")
    public static <A> Iterator<A> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    @SafeVarargs
    public static <A> Iterator<A> valueIterator(A... as) {
        return Arrays.asList(as).iterator();
    }

    @SafeVarargs
    public static <A> Iterator<A> cyclicIterator(final A... as) {
        if (as.length == 0) {
            return emptyIterator();
        } else {
            final List<A> list = Arrays.asList(as);
            return new Iterator<A>() {

                private Iterator<A> it = list.iterator();

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public A next() {
                    if (!it.hasNext()) {
                        it = list.iterator();
                    }
                    return it.next();
                }
            };
        }
    }

    @SafeVarargs
    public static <A> Iterator<A> concat(final Iterator<A>... its) {
        if (its.length == 0) {
            return emptyIterator();
        } else {
            return new Iterator<A>() {

                private int index = 1;
                private Iterator<A> it = its[0];

                @Override
                public boolean hasNext() {
                    while (!it.hasNext() && index < its.length) {
                        it = its[index++];
                    }
                    return it.hasNext();
                }

                @Override
                public A next() {
                    Contracts.require(hasNext(), "next() called on empty iterator", NoSuchElementException.class);
                    return it.next();
                }
            };
        }
    }

    public static <A> Iterator<A> drop(int n, Iterator<A> it) {
        for(int i = 0; i < n && it.hasNext(); i++) {
            it.next();
        }
        return it;
    }

    public static <A,B,C> Iterator<C> zipWith(Iterator<A> iteratorA, Iterator<B> iteratorB, BiFunction<A,B,C> fn) {
        return new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return iteratorA.hasNext() && iteratorB.hasNext();
            }

            @Override
            public C next() {
                return fn.apply(iteratorA.next(), iteratorB.next());
            }
        };
    }

    public static <A,B> Iterator<T2<A,B>> zip(Iterator<A> iteratorA, Iterator<B> iteratorB) {
        return zipWith(iteratorA, iteratorB, T2::of);
    }
}
