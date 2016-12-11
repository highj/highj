package org.highj.data.tuple.t3;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asT3;

public interface T3Apply<S, T> extends T3Functor<S, T>, Apply<__<__<T3.µ, S>, T>> {

    Semigroup<S> getS();

    Semigroup<T> getT();

    @Override
    default <A, B> T3<S, T, B> ap(
            __<__<__<T3.µ, S>, T>, Function<A, B>> fn,
            __<__<__<T3.µ, S>, T>, A> nestedA) {
        T3<S, T, Function<A, B>> fnTriple = asT3(fn);
        T3<S, T, A> aTriple = asT3(nestedA);
        return T3.of(getS().apply(fnTriple._1(), aTriple._1()),
                getT().apply(fnTriple._2(), aTriple._2()),
                fnTriple._3().apply(aTriple._3()));
    }
}
