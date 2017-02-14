package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.derive4j.hkt.TypeEq.as__2;

//minimal implementation: bimap OR (first AND second)
public interface Bifunctor<F> {

    default <A, B, C, D> __2<F, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<F, A, C> nestedAC) {
        return first(fn1, second(fn2, nestedAC));
    }

    default <A, B, C> __2<F, B, C> first(Function<A, B> fn, __2<F, A, C> nestedAC) {
        return bimap(fn, Function.identity(), nestedAC);
    }

    default <A, B, C> __2<F, A, C> second(Function<B, C> fn, __2<F, A, B> nestedAB) {
        return bimap(Function.identity(), fn, nestedAB);
    }

    default <X> Functor<__<F, X>> getFunctor() {
        return (CurriedFunctor<F,X>) () -> this;
    }

    interface CurriedFunctor<F,X> extends Functor<__<F,X>> {

        Bifunctor<F> bi();

        default <A,B> __2<F, X, B> map(Function<A,B> fn, __<__<F, X>, A> nestedA) {
            return bi().second(fn, as__2(nestedA));
        }

    }
}
