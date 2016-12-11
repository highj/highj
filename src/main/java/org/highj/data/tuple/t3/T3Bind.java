package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asT3;

public interface T3Bind<S, T> extends T3Apply<S, T>, Bind<__<__<T3.µ, S>, T>> {

    @Override
    default <A, B> T3<S, T, B> bind(
            __<__<__<T3.µ, S>, T>, A> nestedA,
            Function<A, __<__<__<T3.µ, S>, T>, B>> fn) {
        T3<S, T, A> ta = asT3(nestedA);
        T3<S, T, B> tb = asT3(fn.apply(ta._3()));
        return T3.of(getS().apply(ta._1(), tb._1()), getT().apply(ta._2(), tb._2()), tb._3());
    }
}
