package org.highj.function;

import org.highj.data.Maybe;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public enum Functions {
    ;

    public static <A, B, C> Function<A, Function<B, C>> curry2(Function<T2<A, B>, C> fn) {
        return a -> b -> fn.apply(T2.of(a, b));
    }

    public static <A, B, C> Function<T2<A, B>, C> uncurry2(Function<A, Function<B, C>> fn) {
        return pair -> fn.apply(pair._1()).apply(pair._2());
    }

    public static <A, B, C, D> Function<A, Function<B, Function<C, D>>> curry3(Function<T3<A, B, C>, D> fn) {
        return a -> b -> c -> fn.apply(T3.of(a, b, c));
    }

    public static <A, B, C, D> Function<T3<A, B, C>, D> uncurry3(Function<A, Function<B, Function<C, D>>> fn) {
        return triple -> fn.apply(triple._1()).apply(triple._2()).apply(triple._3());
    }


    public static <A, B, C, D, E> Function<A, Function<B, Function<C, Function<D, E>>>> curry4(Function<T4<A, B, C, D>, E> fn) {
        return a -> b -> c -> d -> fn.apply(T4.of(a, b, c, d));
    }

    public static <A, B, C, D, E> Function<T4<A, B, C, D>, E> uncurry4(Function<A, Function<B, Function<C, Function<D, E>>>> fn) {
        return quadruple -> fn.apply(quadruple._1()).apply(quadruple._2()).apply(quadruple._3()).apply(quadruple._4());
    }

    public static <A, B> Function<A, Function<B, A>> constant() {
        return a -> b -> a;
    }

    public static <A, B> Function<A, B> constant(final B b) {
        return a -> b;
    }

    public static <A, B> Function<A, B> constant(final Supplier<B> thunk) {
        return a -> thunk.get();
    }

    public static <A, B, C> Function<A, C> compose(final Function<? super B, ? extends C> f, final Function<? super A, ? extends B> g) {
        return a -> f.apply(g.apply(a));
    }

    public static <A, B, C, D> Function<A, D> compose(final Function<? super C, ? extends D> f, final Function<? super B, ? extends C> g, final Function<? super A, ? extends B> h) {
        return a -> f.apply(g.apply(h.apply(a)));
    }

    public static <A, B, C> Function<B, Function<A, C>> flip(Function<A, Function<B, C>> fn) {
        return b -> a -> fn.apply(a).apply(b);
    }

    public static <A, B, C, D> Function<A, Function<B, D>> compose2(final Function<? super C, ? extends D> f, final Function<? super A, Function<? super B, ? extends C>> g) {
        return a -> b -> f.apply(g.apply(a).apply(b));
    }

    public static <A> Monoid<Function<A, A>> endoMonoid() {
        return Monoid.create(Function.<A>identity(), (f, g) -> x -> g.apply(f.apply(x)));
    }

    public static <A, B> Function<A, Function<Function<A, B>, B>> flipApply() {
        return a -> fn -> fn.apply(a);
    }

    public static <A, B, C> Function<A, T2<B, C>> fanout(Function<A, B> fab, Function<A, C> fac) {
        return a -> T2.of(fab.apply(a), fac.apply(a));
    }

    public static <A, B, C, D> Function<A, T3<B, C, D>> fanout(Function<A, B> fab, Function<A, C> fac, Function<A, D> fad) {
        return a -> T3.of(fab.apply(a), fac.apply(a), fad.apply(a));
    }

    public static <A, B, C, D, E> Function<A, T4<B, C, D, E>> fanout(Function<A, B> fab, Function<A, C> fac, Function<A, D> fad, Function<A, E> fae) {
        return a -> T4.of(fab.apply(a), fac.apply(a), fad.apply(a), fae.apply(a));
    }

    public static <A> Supplier<A> fromFunction(final Function<T0, A> fn) {
        return () -> fn.apply(T0.unit);
    }

    public static <A> Supplier<A> constantF0(final A a) {
        return () -> a;
    }

    public static <A> Supplier<A> error(final String message) {
        return () -> {
            throw new RuntimeException(message);
        };
    }

    public static <A> Supplier<A> error(final Class<? extends RuntimeException> exClass) {
        return () -> {
            RuntimeException exception;
            try {
                exception = exClass.newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw exception;
        };
    }

    public static <A> Supplier<A> error(final Class<? extends RuntimeException> exClass, final String message) {
        return () -> {
            RuntimeException exception;
            try {
                exception = exClass.getConstructor(String.class).newInstance(message);
            } catch (Exception ex) {
                throw new AssertionError(ex);
            }
            throw exception;
        };
    }

    public static <A, B> Supplier<B> lazy(final Function<A, B> fn, final A a) {
        return () -> fn.apply(a);
    }

    public static <A, B, C> Supplier<C> lazy(final Function<A, Function<B, C>> fn, final A a, final B b) {
        return () -> fn.apply(a).apply(b);
    }

    public static <A, B, C, D> Supplier<D> lazy(final Function<A, Function<B, Function<C, D>>> fn, final A a, final B b, final C c) {
        return () -> fn.apply(a).apply(b).apply(c);
    }

    public static <A, B, C, D, E> Supplier<E> lazy(final Function<A, Function<B, Function<C, Function<D, E>>>> fn, final A a, final B b, final C c, final D d) {
        return () -> fn.apply(a).apply(b).apply(c).apply(d);
    }

    public static <A, B> Function<A, Maybe<B>> fromJavaMap(Map<A, B> map) {
        return a -> Maybe.JustWhenTrue(map.containsKey(a), () -> map.get(a));
    }

    public static <A, B> Function<A, B> fromJavaMap(Map<A, B> map, B defaultValue) {
        return a -> map.containsKey(a) ? map.get(a) : defaultValue;
    }

}
