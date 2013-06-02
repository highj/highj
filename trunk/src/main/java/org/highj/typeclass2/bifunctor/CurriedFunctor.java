package org.highj.typeclass2.bifunctor;

import org.highj.HigherKinded;
import org.highj._;
import org.highj.__;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public class CurriedFunctor<F,X> implements Functor<__.µ<F,X>> {

    private final BiFunctor<F> bifunctor;

    public CurriedFunctor(BiFunctor<F> bifunctor) {
        this.bifunctor = bifunctor;
    }

    @Override
    public <A,B> _<__.µ<F, X>, B> map(Function<A,B> fn, _<__.µ<F, X>, A> nestedA) {
        return bifunctor.rightMap(fn, HigherKinded.uncurry2(nestedA));
    }

}
