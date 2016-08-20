package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T4Monad<S, T, U> extends T4Applicative<S, T, U>, T4Bind<S, T, U>, Monad<__<__<__<T4.µ, S>, T>, U>> {
    @Override
    Monoid<S> getS();

    @Override
    Monoid<T> getT();

    @Override
    Monoid<U> getU();


    default <A, B> T4<S, T, U, B> ap(
            __<__<__<__<T4.µ, S>, T>, U>, Function<A, B>> fn,
            __<__<__<__<T4.µ, S>, T>, U>, A> nestedA) {
        return T4Applicative.super.ap(fn, nestedA);
    }
}
