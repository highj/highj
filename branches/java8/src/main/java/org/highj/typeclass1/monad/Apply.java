package org.highj.typeclass1.monad;

import org.highj._;

import java.util.function.Function;

public interface Apply<µ> extends Functor<µ> {

    // <*> (Control.Applicative), ap (Control.Monad)
    public <A, B> _<µ, B> ap(_<µ, Function<A, B>> fn, _<µ, A> nestedA);

    // <* (Control.Applicative)
    public default <A, B> _<µ, A> leftSeq(_<µ, A> nestedA, _<µ, B> nestedB) {
        return lift2((A a) -> (B b) -> a).apply(nestedA).apply(nestedB);
    }

    // *> (Control.Applicative)
    public default <A, B> _<µ, B> rightSeq(_<µ, A> nestedA, _<µ, B> nestedB) {
        return leftSeq(nestedB, nestedA);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public default <A, B, C> Function<_<µ, A>, Function<_<µ, B>, _<µ, C>>> lift2(final Function<A, Function<B, C>> fn) {
        return a -> b -> ap(map(fn, a), b);
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public default <A, B, C, D> Function<_<µ, A>, Function<_<µ, B>, Function<_<µ, C>, _<µ, D>>>> lift3(final Function<A, Function<B, Function<C, D>>> fn) {
        return a -> b -> c -> ap(lift2(fn).apply(a).apply(b),c);
    }

}
