package org.highj.data.structural;

import org.highj.__;
import org.highj.data.structural.constant.ConstApplicative;
import org.highj.data.structural.constant.ConstApply;
import org.highj.data.structural.constant.ConstContravariant;
import org.highj.data.structural.constant.ConstFunctor;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;

public class Const<A, B> implements __<Const.µ, A, B> {

    public static class µ {
    }

    private final A value;

    public Const(A value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> Const<A, B> narrow(org.highj._<__.µ<µ, A>, B> value) {
        return (Const) value;
    }

    public A get() {
        return value;
    }

    public static <S> Functor<__.µ<µ, S>> functor() {
        return new ConstFunctor<S>() {
        };
    }

    public static <S> Apply<__.µ<µ, S>> apply(final Semigroup<S> semigroup) {
        return (ConstApply<S>) () -> semigroup;
    }

    public static <S> Applicative<__.µ<µ, S>> applicative(final Monoid<S> monoid) {
        return (ConstApplicative<S>) () -> monoid;
    }

    public static <S> Contravariant<__.µ<µ, S>> contravariant() {
        return new ConstContravariant<S>() {
        };
    }
}
