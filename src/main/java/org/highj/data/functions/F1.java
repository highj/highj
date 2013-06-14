package org.highj.data.functions;

import org.highj._;
import org.highj.__;
import org.highj.data.functions.f1.F1Monad;
import org.highj.data.functions.f1.F1Arrow;
import org.highj.data.functions.f1.F1EndoMonoid;
import org.highj.data.tuple.*;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class representing an unary function.
 */
@FunctionalInterface
public interface F1<A, B> extends  __<F1.µ, A, B>, Function<A,B> {

    public static class µ {
    }


    static <A> F1<A, A> id() {
        return a -> a;
    }

    @SuppressWarnings("unchecked")
    static <A, B> F1<A, B> narrow(_<__.µ<µ, A>, B> function) {
        return (F1) function;
    }

    @SuppressWarnings("unchecked")
    static <A, Super_B, B extends Super_B> F1<A, Super_B> contravariant(F1<A, B> function) {
        return (F1) function;
    }

    static <A, B> F1<A, F1<B, A>> constant() {
        return a -> b -> a;
    }

    static <A, B> F1<A, B> constant(final B b) {
        return a -> b;
    }

    static <A, B> F1<A, B> constant(final Supplier<B> thunk) {
        return a -> thunk.get();
    }

    static <A, B, C> F1<A, C> compose(final Function<? super B, ? extends C> f, final Function<? super A, ? extends B> g) {
        return a -> f.apply(g.apply(a));
    }

    static <A, B, C, D> F1<A, D> compose(final Function<? super C, ? extends D> f, final Function<? super B, ? extends C> g, final Function<? super A, ? extends B> h) {
        return a -> f.apply(g.apply(h.apply(a)));
    }

    static <A, B> F1<F1<A, B>, B> flip(final A a) {
        return fn -> fn.apply(a);
    }

    static <A> Monoid<F1<A,A>> endoMonoid() {
        return new F1EndoMonoid<>();
    }

    static <A, B> F1<A, F1<F1<A, B>, B>> flipApply() {
        return a -> fn -> fn.apply(a);
    }

    static <R> F1Monad<R> monad() {
        return new F1Monad<>();
    }

    static <A, B, C> F1<A, T2<B, C>> fanout(_<__.µ<µ, A>, B> fab, _<__.µ<µ, A>, C> fac) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a));
    }

    static <A, B, C, D> F1<A, T3<B, C, D>> fanout(_<__.µ<µ, A>, B> fab, _<__.µ<µ, A>, C> fac, _<__.µ<µ, A>, D> fad) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a), fnad.apply(a));
    }

    static <A, B, C, D, E> F1<A, T4<B, C, D, E>> fanout(_<__.µ<µ, A>, B> fab, _<__.µ<µ, A>, C> fac, _<__.µ<µ, A>, D> fad, _<__.µ<µ, A>, E> fae) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        final F1<A, E> fnae = narrow(fae);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a), fnad.apply(a), fnae.apply(a));
    }

    static <A> Supplier<A> fromF1(final F1<T0, A> fn) {
        return () -> fn.apply(T0.unit);
    }

    static <A, B> Supplier<B> lazy(final F1<A, B> fn, final A a) {
        return () -> fn.apply(a);
    }


    public default Supplier<B> lazy(A a) {
        return lazy(this, a);
    }

    public default <C> F1<A, C> andThen(_<__.µ<µ, B>, C> that) {
        return compose(narrow(that), this);
    }

    public static F1Arrow arrow = new F1Arrow();


}
