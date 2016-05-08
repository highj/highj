package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public class CurriedApply<F,X> extends CurriedFunctor<F,X> implements Apply<__<F,X>> {

    private final Biapply<F> biapply;
    private final Semigroup<X> semigroup;

    public CurriedApply(Biapply<F> biapply, Semigroup<X> semigroup) {
        super(biapply);
        this.biapply = biapply;
        this.semigroup = semigroup;
    }


    @Override
    public <A, B> __2<F, X, B> ap(__<__<F, X>, Function<A, B>> fn, __<__<F, X>, A> nestedA) {
        __2<F,X,Function<A,B>> uncurriedFn = __2.coerce(fn);
        Function<X, Function<X,X>> dotFn = x -> y -> semigroup.apply(x, y);
        __2<F,Function<X,X>, Function<A,B>> biFn = biapply.first(dotFn, uncurriedFn);
        return biapply.biapply(biFn, __2.coerce(nestedA));
    }
}
