package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface T2Monad<S> extends T2Bind<S>, T2Applicative<S>, Monad<__<T2.µ, S>> {

    @Override
    Monoid<S> get();

    default <A, B> T2<S, B> ap(__<__<T2.µ, S>, Function<A, B>> fn, __<__<T2.µ, S>, A> nestedA) {
        return T2Applicative.super.ap(fn, nestedA);
    }
}
