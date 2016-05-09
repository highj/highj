package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public class CurriedFunctor<F,X> implements Functor<__<F,X>> {

    private final Bifunctor<F> bifunctor;

    public CurriedFunctor(Bifunctor<F> bifunctor) {
        this.bifunctor = bifunctor;
    }

    @Override
    public <A,B> __2<F, X, B> map(Function<A,B> fn, __<__<F, X>, A> nestedA) {
        return bifunctor.second(fn, __2.coerce(nestedA));
    }

}
