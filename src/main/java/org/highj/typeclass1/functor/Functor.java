package org.highj.typeclass1.functor;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.function.Functions;
import org.highj.typeclass1.invariant.Invariant;

import java.util.function.Function;

public interface Functor<F> extends Invariant<F> {

    <A, B> __<F, B> map(Function<A, B> fn, __<F, A> nestedA);

    default <A, B> __<F, B> invmap(Function<A, B> fn, Function<B,A> nf, __<F, A> nestedA) {
        return map(fn, nestedA);
    }

    default <A, B> __<F, A> left$(A a, __<F, B> nestedB) {
        return map(Functions.<B,A>constant(a), nestedB);
    }

    default <A> __<F, T0> voidF(__<F, A> nestedA) {
        return left$(T0.unit, nestedA);
    }

    default <A, B> __<F, B> flip(__<F, Function<A, B>> nestedFn, final A a) {
        return map(fn -> fn.apply(a), nestedFn);
    }

    default <A, B> Function<__<F, A>, __<F, B>> lift(final Function<A, B> fn) {
        return a -> map(fn, a);
    }

    default <A, B> T2<__<F, A>, __<F, B>> unzip(__<F, T2<A, B>> fab) {
        return T2.of(
            lift((Function<T2<A, B>, A>) T2::_1).apply(fab),
            lift((Function<T2<A, B>, B>) T2::_2).apply(fab));
    }

    static <X, Y, A, B> Function<__<X, __<Y, A>>, __<X, __<Y, B>>> binary(
            Functor<X> fx, Functor<Y> fy, Function<A, B> fn) {
        return fx.lift(fy.lift(fn));
    }

    static <X, Y, Z, A, B> Function<__<X, __<Y, __<Z, A>>>, __<X, __<Y, __<Z, B>>>> ternary(
            Functor<X> fx, Functor<Y> fy, Functor<Z> fz, Function<A, B> fn) {
        return fx.lift(fy.lift(fz.lift(fn)));
    }
}
