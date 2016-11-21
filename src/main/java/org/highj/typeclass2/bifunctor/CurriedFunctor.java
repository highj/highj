package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.derive4j.hkt.TypeEq.as__2;

public class CurriedFunctor<F,X> implements Functor<__<F,X>> {

    private final Bifunctor<F> bifunctor;

    public CurriedFunctor(Bifunctor<F> bifunctor) {
        this.bifunctor = bifunctor;
    }

    @Override
    public <A,B> __2<F, X, B> map(Function<A,B> fn, __<__<F, X>, A> nestedA) {
        return bifunctor.second(fn, as__2(nestedA));
    }

}
