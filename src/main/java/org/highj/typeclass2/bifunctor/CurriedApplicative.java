package org.highj.typeclass2.bifunctor;

import org.highj._;
import org.highj.__;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public class CurriedApplicative<F,X> extends CurriedApply<F,X> implements Applicative<__.µ<F,X>> {

    private final BiApplicative<F> biApplicative;
    private final Monoid<X> monoid;

    public CurriedApplicative(BiApplicative<F> biApplicative, Monoid<X> monoid) {
        super(biApplicative, monoid);
        this.biApplicative = biApplicative;
        this.monoid = monoid;
    }

    @Override
    public <A> __<F, X, A> pure(A a) {
        return biApplicative.biPure(monoid.identity(),a);
    }
}
