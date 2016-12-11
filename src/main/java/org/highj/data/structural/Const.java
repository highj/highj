package org.highj.data.structural;

import org.derive4j.hkt.HktConfig;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.structural.constant.*;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;

import java.util.function.Supplier;

@HktConfig(typeEqMethodName = "const_")
public class Const<A, B> implements __2<Const.µ, A, B>, Supplier<A> {

    public interface µ {
    }

    private final A value;

    public Const(A value) {
        this.value = value;
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

    public static <S> ConstDivisible<S> divisible(Monoid<S> monoid) {
        return () -> monoid;
    }

    public static final ConstBiapplicative biapplicative = new ConstBiapplicative() {
    };

    public static <M,A> Semigroup<Const<M,A>> semigroup(Semigroup<M> semigroup) {
        return (x,y) -> new Const<>(semigroup.apply(x.get(), y.get()));
    }

    public static <M,A> Monoid<Const<M,A>> monoid(Monoid<M> monoid) {
        return Monoid.create(new Const<>(monoid.identity()),
                (x,y) -> new Const<>(monoid.apply(x.get(), y.get())));
    }

    public static <M,A> Group<Const<M,A>> group(Group<M> group) {
        return Group.create(new Const<>(group.identity()),
                (x,y) -> new Const<>(group.apply(x.get(), y.get())),
                z -> new Const<>(group.inverse(z.get())));
    }
}
