package org.highj.typeclass1.functor;

import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

//Minimal complete definition: map OR lift
public interface Functor<F> extends Invariant<F> {

    // fmap (Data.Functor)
    public <A, B> _<F, B> map(Function<A, B> fn, _<F, A> nestedA);

    public default <A, B> _<F, B> invmap(Function<A, B> fn, Function<B,A> nf, _<F, A> nestedA) {
        return map(fn, nestedA);
    }

    // <$  (Data.Functor)
    public default <A, B> _<F, A> left$(A a, _<F, B> nestedB) {
        return map(Functions.<B,A>constant(a), nestedB);
    }

    //void (Control.Monad)
    public default <A> _<F, T0> voidF(_<F, A> nestedA) {
        return left$(T0.unit, nestedA);
    }

    //flip (Data.Functor.Syntax)
    public default <A, B> _<F, B> flip(_<F, Function<A, B>> nestedFn, final A a) {
        return map(fn -> fn.apply(a), nestedFn);
    }

    //liftA (Control.Applicative), liftM (Control.Monad), curried version of fmap
    public default <A, B> Function<_<F, A>, _<F, B>> lift(final Function<A, B> fn) {
        return a -> map(fn, a);
    }
}
