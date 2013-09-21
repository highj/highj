package org.highj.data.collection.dequeue;

import org.highj._;
import org.highj.data.collection.Dequeue;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface DequeueFunctor extends Functor<Dequeue.µ> {
    @Override
    public default <A, B> Dequeue<B> map(final Function<A, B> fn, _<Dequeue.µ, A> nestedA) {
        return Dequeue.narrow(nestedA).map(fn);
    }
}
