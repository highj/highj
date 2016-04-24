package org.highj.data.collection.dequeue;

import org.highj._;
import org.highj.data.collection.Dequeue;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.data.collection.Dequeue.narrow;
import static org.highj.data.collection.Dequeue.µ;

public interface DequeueTraversable extends Traversable<µ>, DequeueFunctor {

    default <A, B> A foldl(Function<A, Function<B, A>> fn, A a, _<µ, B> bs) {
        return narrow(bs).foldl(a, fn);
    }

    default <A, B> B foldr(Function<A, Function<B, B>> fn, B b, _<µ, A> as) {
        return narrow(as).foldr(fn, b);
    }

    @Override
    default <A, B> Dequeue<B> map(Function<A, B> fn, _<µ, A> as) {
        return DequeueFunctor.super.map(fn, as);
    }

    @Override
    default <A, B, X> _<X, _<Dequeue.µ, B>> traverse(Applicative<X> applicative, Function<A, _<X, B>> fn, _<Dequeue.µ, A> traversable) {
        //traverse f = Prelude.foldr cons_f (pure [])
        //  where cons_f x ys = (:) <$> f x <*> ys
        Dequeue<A> dequeueA = narrow(traversable);
        _<Dequeue.µ, B> emptyB = Dequeue.empty();
        return dequeueA.foldr(a -> bs ->
                        applicative.ap(applicative.map(e -> es ->
                                Dequeue.narrow(es).pushFront(e), fn.apply(a)), bs),
                applicative.pure(emptyB));
    }
}
