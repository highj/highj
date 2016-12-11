package org.highj.data.instance.dequeue;

import org.derive4j.hkt.__;
import org.highj.data.Dequeue;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asDequeue;

public interface DequeueFunctor extends Functor<Dequeue.µ> {
    @Override
    default <A, B> Dequeue<B> map(final Function<A, B> fn, __<Dequeue.µ, A> nestedA) {
        return asDequeue(nestedA).map(fn);
    }
}
