package org.highj.typeclass1.functor;

import org.highj._;

import java.util.function.Function;

public enum Functors {
    ;

    //(.:) , binary (Data.Functor.Syntax)
    public static <X, Y, A, B> Function<_<X, _<Y, A>>, _<X, _<Y, B>>> binary(
            Functor<X> fx, Functor<Y> fy, Function<A, B> fn) {
        return fx.lift(fy.lift(fn));
    }

    //(.::) , ternary (Data.Functor.Syntax)
    public static <X, Y, Z, A, B> Function<_<X, _<Y, _<Z, A>>>, _<X, _<Y, _<Z, B>>>> ternary(
            Functor<X> fx, Functor<Y> fy, Functor<Z> fz, Function<A, B> fn) {
        return fx.lift(fy.lift(fz.lift(fn)));
    }
}
