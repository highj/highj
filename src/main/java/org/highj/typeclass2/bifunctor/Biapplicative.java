package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public interface Biapplicative<F> extends Biapply<F> {

    public <A,B> __2<F,A,B> bipure(A a, B b);

    public default <X> Applicative<__<F,X>> getApplicative(Monoid<X> monoid) {
        return new CurriedApplicative<>(this, monoid);
    }

}
