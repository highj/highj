package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface Apply<M> extends Functor<M> {

    // <*> (Control.Applicative), ap (Control.Monad)
    public <A, B> _<M, B> ap(_<M, Function<A, B>> fn, _<M, A> nestedA);

    // <* (Control.Applicative)
    public default <A, B> _<M, A> leftSeq(_<M, A> nestedA, _<M, B> nestedB) {
        return lift2((A a) -> (B b) -> a).apply(nestedA).apply(nestedB);
    }

    // *> (Control.Applicative)
    public default <A, B> _<M, B> rightSeq(_<M, A> nestedA, _<M, B> nestedB) {
        return leftSeq(nestedB, nestedA);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public default <A, B, C> Function<_<M, A>, Function<_<M, B>, _<M, C>>> lift2(final Function<A, Function<B, C>> fn) {
        return a -> b -> ap(map(fn, a), b);
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public default <A, B, C, D> Function<_<M, A>, Function<_<M, B>, Function<_<M, C>, _<M, D>>>> lift3(final Function<A, Function<B, Function<C, D>>> fn) {
        return a -> b -> c -> ap(lift2(fn).apply(a).apply(b),c);
    }

}
