package org.highj.typeclass1.contravariant;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;

import java.util.function.Function;

/**
 * The contravariant equivalent of Applicative.
 *
 * Mirrors Data.Functor.Contravariant.Divisible
 *
 * @param <F> the divisible instance
 */
public interface Divisible<F> extends Contravariant<F> {

    <A,B,C> __<F,A> divide(Function<A,T2<B,C>> fn, __<F,B> fb, __<F,C> fc);

    <A> __<F,A> conquer();

    default <A,B> __<F,T2<A,B>> divided(__<F,A> fa, __<F,B> fb) {
        return divide(Function.identity(), fa, fb);
    }

    default __<F, T0> conquered() {
        return conquer();
    }

    default <A,B> Function<__<F,B>, __<F,A>> liftD(Function<A,B> fn) {
        return fb -> divide(a -> T2.of(T0.of(), fn.apply(a)), conquer(), fb);
    }
}
