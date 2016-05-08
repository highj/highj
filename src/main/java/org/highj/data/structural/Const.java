package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.structural.constant.*;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

public class Const<A, B> implements __2<Const.µ, A, B> {

    public static class µ {
    }

    private final A value;

    public Const(A value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> Const<A, B> narrow(__<__<µ, A>, B> value) {
        return (Const) value;
    }

    public A get() {
        return value;
    }

    public static <S> ConstFunctor<S> functor() {
        return new ConstFunctor<S>() {
        };
    }

    public static <S> ConstApply<S> apply(final Semigroup<S> semigroup) {
        return () -> semigroup;
    }

    public static <S> ConstApplicative<S> applicative(final Monoid<S> monoid) {
        return () -> monoid;
    }

    public static <S> ConstContravariant<S> contravariant() {
        return new ConstContravariant<S>() {
        };
    }

    public static final ConstBiapplicative biapplicative = new ConstBiapplicative() {
    };
}
