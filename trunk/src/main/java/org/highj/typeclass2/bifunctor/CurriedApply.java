package org.highj.typeclass2.bifunctor;

import org.highj.HigherKinded;
import org.highj._;
import org.highj.__;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public class CurriedApply<F,X> extends CurriedFunctor<F,X> implements Apply<__.µ<F,X>> {

    private final Biapply<F> biapply;
    private final Semigroup<X> semigroup;

    public CurriedApply(Biapply<F> biapply, Semigroup<X> semigroup) {
        super(biapply);
        this.biapply = biapply;
        this.semigroup = semigroup;
    }


    @Override
    public <A, B> __<F, X, B> ap(_<__.µ<F, X>, Function<A, B>> fn, _<__.µ<F, X>, A> nestedA) {
        __<F,X,Function<A,B>> uncurriedFn = HigherKinded.uncurry2(fn);
        Function<X, Function<X,X>> dotFn = x -> y -> semigroup.dot(x,y);
        __<F,Function<X,X>, Function<A,B>> biFn = biapply.first(dotFn, uncurriedFn);
        return biapply.biapply(biFn, HigherKinded.uncurry2(nestedA));
    }
}
