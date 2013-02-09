package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.function.Functions;

import java.util.function.Function;

//Minimal complete definition: map OR lift
public interface Functor<µ> {

    // fmap (Data.Functor)
    public default <A, B> _<µ, B> map(Function<A, B> fn, _<µ, A> nestedA) {
        return lift(fn).apply(nestedA);
    }

    // <$  (Data.Functor)
    public default <A, B> _<µ, A> left$(A a, _<µ, B> nestedB) {
        return map(Functions.<B,A>constant(a), nestedB);
    }

    //void (Control.Monad)
    public default <A> _<µ, T0> voidF(_<µ, A> nestedA) {
        return left$(T0.unit, nestedA);
    }

    //flip (Data.Functor.Syntax)
    public default <A, B> _<µ, B> flip(_<µ, Function<A, B>> nestedFn, final A a) {
        return map((Function<A, B> fn) -> fn.apply(a), nestedFn);
    }

    //liftA (Control.Applicative), liftM (Control.Monad), curried version of fmap
    public default <A, B> Function<_<µ, A>, _<µ, B>> lift(final Function<A, B> fn) {
        return a -> map(fn, a);
    }
}
