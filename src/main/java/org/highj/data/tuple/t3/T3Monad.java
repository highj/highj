package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T3Monad<S, T> extends T3Bind<S, T>, T3Applicative<S, T>, Monad<__<__<T3.µ, S>, T>> {

    @Override
    Monoid<S> getS();

    @Override
    Monoid<T> getT();

    default <A, B> T3<S, T, B> ap(
            __<__<__<T3.µ, S>, T>, Function<A, B>> fn,
            __<__<__<T3.µ, S>, T>, A> nestedA) {
        return T3Applicative.super.ap(fn, nestedA);
    }
}
