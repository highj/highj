package org.highj.typeclass2.bifunctor;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

public interface Biapplicative<F> extends Biapply<F> {

    <A,B> __2<F,A,B> bipure(A a, B b);

    default <X> Applicative<__<F,X>> getApplicative(Monoid<X> monoid) {
        return new CurriedApplicative<F,X>() {

            @Override
            public Biapplicative<F> bi() {
                return Biapplicative.this;
            }

            @Override
            public Monoid<X> semi() {
                return monoid;
            }
        };
    }

    interface CurriedApplicative<F,X> extends Biapply.CurriedApply<F,X>, Applicative<__<F,X>> {

        @Override
        Biapplicative<F> bi();

        @Override
        Monoid<X> semi();

        @Override
        default <A> __2<F, X, A> pure(A a) {
            return bi().bipure(semi().identity(), a);
        }
    }
}
