package org.highj.typeclass2.bifunctor;

import org.highj.__;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public interface BiApplicative<F> extends BiApply<F> {

    public <A,B> __<F,A,B> biPure(A a, B b);

    public default <X> Applicative<__.µ<F,X>> getApplicative(Monoid<X> monoid) {
        return new CurriedApplicative<>(this, monoid);
    }

}
