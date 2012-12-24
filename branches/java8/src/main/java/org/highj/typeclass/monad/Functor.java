package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.function.F2;

//Minimal complete definition: map OR lift
public interface Functor<mu> {

    // fmap (Data.Functor)
    public default <A, B> _<mu, B> map(F1<A, B> fn, _<mu, A> nestedA) {
        return lift(fn).$(nestedA);
    }

    // <$  (Data.Functor)
    public default <A, B> _<mu, A> left$(A a, _<mu, B> nestedB) {
        return map(F2.<A, B>constant().$(a), nestedB);
    }

    //void (Control.Monad)
    public default <A> _<mu, T0> voidF(_<mu, A> nestedA) {
        return left$(T0.unit, nestedA);
    }

    //flip (Data.Functor.Syntax)
    public default <A, B> _<mu, B> flip(_<mu, F1<A, B>> nestedFn, final A a) {
        return map(new F1<F1<A, B>, B>() {
            @Override
            public B $(F1<A, B> fn) {
                return fn.$(a);
            }
        }, nestedFn);
    }

    //liftA (Control.Applicative), liftM (Control.Monad), curried version of fmap
    public default <A, B> F1<_<mu, A>, _<mu, B>> lift(final F1<A, B> fn) {
        return new F1<_<mu, A>, _<mu, B>>() {
            @Override
            public _<mu, B> $(_<mu, A> a) {
                return map(fn, a);
            }
        };
    }
}
