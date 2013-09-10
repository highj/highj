package org.highj.typeclass2.bifunctor;

import org.highj.__;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public class CurriedApplicative<F,X> extends CurriedApply<F,X> implements Applicative<__.µ<F,X>> {

    private final Biapplicative<F> biapplicative;
    private final Monoid<X> monoid;

    public CurriedApplicative(Biapplicative<F> biapplicative, Monoid<X> monoid) {
        super(biapplicative, monoid);
        this.biapplicative = biapplicative;
        this.monoid = monoid;
    }

    @Override
    public <A> __<F, X, A> pure(A a) {
        return biapplicative.bipure(monoid.identity(), a);
    }
}
