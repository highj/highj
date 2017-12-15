package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.Memo;
import org.highj.data.tuple.*;
import org.highj.function.f1.*;
import org.highj.typeclass0.group.Monoid;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.highj.Hkt.asF1;

/**
 * A class representing an unary function.
 */
@FunctionalInterface
public interface F1<A, B> extends __2<F1.µ, A, B>, Function<A, B> {

    interface µ {
    }

    static <A> F1<A, A> id() {
        return a -> a;
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
        Memo<B> memo = Memo.of(thunk);
        return a -> memo.get();
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

    static <R> F1Functor<R> functor() {
        return new F1Functor<R>() {
        };
    }

    static <R> F1Contravariant<R> contravariant() {
        return new F1Contravariant<R>() {
        };
    }

    static <R> F1Monad<R> monad() {
        return new F1Monad<R>() {
        };
    }

    F1Profunctor profunctor = new F1Profunctor() {
    };

    F1Arrow arrow = new F1Arrow() {
    };

    static <A, B, C> F1<A, T2<B, C>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac) {
        final F1<A, B> fnab = asF1(fab);
        final F1<A, C> fnac = asF1(fac);
        return a -> T2.of(fnab.apply(a), fnac.apply(a));
    }

    static <A, B, C, D> F1<A, T3<B, C, D>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac, __<__<µ, A>, D> fad) {
        final F1<A, B> fnab = asF1(fab);
        final F1<A, C> fnac = asF1(fac);
        final F1<A, D> fnad = asF1(fad);
        return a -> T3.of(fnab.apply(a), fnac.apply(a), fnad.apply(a));
    }

    static <A, B, C, D, E> F1<A, T4<B, C, D, E>> fanout(__<__<µ, A>, B> fab, __<__<µ, A>, C> fac, __<__<µ, A>, D> fad, __<__<µ, A>, E> fae) {
        final F1<A, B> fnab = asF1(fab);
        final F1<A, C> fnac = asF1(fac);
        final F1<A, D> fnad = asF1(fad);
        final F1<A, E> fnae = asF1(fae);
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
        return compose(asF1(that), this);
    }

    static <A, B> F1<A, Maybe<B>> fromJavaMap(Map<A, B> map) {
        return a -> map.containsKey(a) ? Maybe.Just(map.get(a)) : Maybe.<B>Nothing();
    }

    static <A, B> F1<A, B> fromJavaMap(Map<A, B> map, B defaultValue) {
        return a -> map.containsKey(a) ? map.get(a) : defaultValue;
    }

    static <A, B> F1<A, B> fromFunction(Function<A, B> fn) {
        return fn::apply;
    }

    static <A> A fix(Function<T1<A>,A> k) {
        class Util {
            private Maybe<A> resultOp = Maybe.Nothing();
        }
        final Util util = new Util();
        A result = k.apply(T1.of$(() -> {
            if (util.resultOp.isNothing()) {
                throw new RuntimeException("Value read before it was written to.");
            }
            return util.resultOp.get();
        }));
        util.resultOp = Maybe.Just(result);
        return result;
    }

}
