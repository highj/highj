package org.highj.data.collection.dequeue;

import org.highj._;
import org.highj.data.collection.Dequeue;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;

public interface DequeueFoldable extends Foldable<Dequeue.µ> {

    public default <A, B> B foldr(Function<A, Function<B, B>> fn, B b, _<Dequeue.µ, A> as) {
        return Dequeue.narrow(as).foldr(fn,b);
    }

    public default <A, B> A foldl(Function<A, Function<B, A>> fn, A a, _<Dequeue.µ, B> bs) {
        return Dequeue.narrow(bs).foldl(a, fn);
    }
}
