package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.function.NF;
import org.highj.typeclass1.functor.Functor1;

import java.util.function.BiFunction;

public interface Apply1<M> extends Functor1<M> {

    <A, B> __<M, B> ap(__<M, NF<A, B>> fn, __<M, A> nestedA);

    default <A, B, C> BiFunction<__<M, A>, __<M, B>, __<M, C>> lift2(final NF<A, NF<B, C>> fn) {
        return (a, b) -> ap(map(fn, a), b);
    }
}
