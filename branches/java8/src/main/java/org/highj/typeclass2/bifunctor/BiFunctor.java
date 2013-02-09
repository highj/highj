package org.highj.typeclass2.bifunctor;

import org.highj.HigherKinded;
import org.highj._;
import org.highj.__;
import org.highj.function.Functions;
import org.highj.typeclass1.monad.Functor;

import java.util.function.Function;

//minimal implementation: biMap OR (leftMap AND rightMap)
public interface BiFunctor<F> {

    // bimap (Data.BiFunctor)
    public default <A, B, C, D> __<F, B, D> biMap(Function<A, B> fn1, Function<C, D> fn2, __<F, A, C> nestedAC) {
        return leftMap(fn1, rightMap(fn2, nestedAC));
    }

    // first (Data.BiFunctor)
    public default <A, B, C> __<F, B, C> leftMap(Function<A, B> fn, __<F, A, C> nestedAC) {
        return biMap(fn, Functions.<C>id(), nestedAC);
    }

    // second (Data.BiFunctor)
    public default <A, B, C> __<F, A, C> rightMap(Function<B, C> fn, __<F, A, B> nestedAB) {
        return biMap(Functions.<A>id(), fn, nestedAB);
    }

    //functionality of rightMap as a Functor (with left-curried argumets)
    public default <X> Functor<__.µ<F, X>> getFunctor() {
        return new CurriedFunctor<>(this);
    }
}
