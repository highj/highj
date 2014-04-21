package org.highj.typeclass2.bifunctor;

import org.highj.__;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

//minimal implementation: bimap OR (first AND second)
public interface Bifunctor<F> {

    // bimap (Data.Bifunctor)
    public default <A, B, C, D> __<F, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<F, A, C> nestedAC) {
        return first(fn1, second(fn2, nestedAC));
    }

    // first (Data.Bifunctor)
    public default <A, B, C> __<F, B, C> first(Function<A, B> fn, __<F, A, C> nestedAC) {
        return bimap(fn, Function.<C>identity(), nestedAC);
    }

    // second (Data.Bifunctor)
    public default <A, B, C> __<F, A, C> second(Function<B, C> fn, __<F, A, B> nestedAB) {
        return bimap(Function.<A>identity(), fn, nestedAB);
    }

    //functionality of second as a Functor (with left-curried argumets)
    public default <X> Functor<__.µ<F, X>> getFunctor() {
        return new CurriedFunctor<>(this);
    }
}
