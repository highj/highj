package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;

public enum Functors {
    ;

    //(.:) , binary (Data.Functor.Syntax)
    public static <X, Y, A, B> F1<_<X, _<Y, A>>, _<X, _<Y, B>>> binary(
            Functor<X> fx, Functor<Y> fy, F1<A, B> fn) {
        return fx.lift(fy.lift(fn));
    }

    //(.::) , trinary (Data.Functor.Syntax)
    public static <X, Y, Z, A, B> F1<_<X, _<Y, _<Z, A>>>, _<X, _<Y, _<Z, B>>>> trinary(
            Functor<X> fx, Functor<Y> fy, Functor<Z> fz, F1<A, B> fn) {
        return fx.lift(fy.lift(fz.lift(fn)));
    }
}
