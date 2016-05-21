package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.function.f1.F1Arrow;
import org.highj.function.f1.F1Monad;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class representing an unary function.
 */
@FunctionalInterface
public interface F1<A, B> extends __2<F1.µ, A, B>, Function<A, B> {

    class µ {
    }

    static <A> F1<A, A> id() {
        return a -> a;
    }

    @SuppressWarnings("unchecked")
    static <A, B> F1<A, B> narrow(__<__<µ, A>, B> function) {
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
        //without memoization we cannot be sure that the constant function is really constant
        return new F1<A, B>() {
            Maybe<B> memo = Maybe.Nothing();

            @Override
            public B apply(A a) {
                if (memo.isJust()) {
                    return memo.get();
                }
                B b = thunk.get();
                memo = Maybe.Just(b);
                return b;
            }
        };
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

    static <A> Monoid<F1<A, A>> endoMonoid() {
        return Monoid.create(F1.id(), (x, y) -> F1.compose(x, y));
    }

    static <A, B> F1<A, F1<F1<A, B>, B>> flipApply() {
        return a -> fn -> fn.apply(a);
    }

    static <R> F1Monad<R> monad() {
        return new F1Monad<>();
    }

    static <A, B, C> F1<A, T2<B, C>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        return a -> T2.of(fnab.apply(a), fnac.apply(a));
    }

    static <A, B, C, D> F1<A, T3<B, C, D>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac, __<__<µ, A>, D> fad) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        return a -> T3.of(fnab.apply(a), fnac.apply(a), fnad.apply(a));
    }

    static <A, B, C, D, E> F1<A, T4<B, C, D, E>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac, __<__<µ, A>, D> fad, __<__<µ, A>, E> fae) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        final F1<A, E> fnae = narrow(fae);
        return a -> T4.of(fnab.apply(a), fnac.apply(a), fnad.apply(a), fnae.apply(a));
    }

    static <A> Supplier<A> fromF1(final F1<T0, A> fn) {
        return () -> fn.apply(T0.unit);
    }

    static <A, B> Supplier<B> lazy(final F1<A, B> fn, final A a) {
        return () -> fn.apply(a);
    }


    default Supplier<B> lazy(A a) {
        return lazy(this, a);
    }

    //avoid name clash with Function.andThen()
    default <C> F1<A, C> then(__<__<µ, B>, C> that) {
        return compose(narrow(that), this);
    }

    F1Arrow arrow = new F1Arrow();
    
    static <A, B> F1<A, Maybe<B>> fromJavaMap(Map<A, B> map) {
        return a -> map.containsKey(a) ? Maybe.Just(map.get(a)) : Maybe.<B>Nothing();
    }

    static <A, B> F1<A, B> fromJavaMap(Map<A, B> map, B defaultValue) {
        return a -> map.containsKey(a) ? map.get(a) : defaultValue;
    }
}
