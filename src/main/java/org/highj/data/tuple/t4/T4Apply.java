package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asT4;

public interface T4Apply<S, T, U> extends T4Functor<S, T, U>, Apply<__<__<__<T4.µ, S>, T>, U>> {

    Semigroup<S> getS();

    Semigroup<T> getT();

    Semigroup<U> getU();

    @Override
    default <A, B> T4<S, T, U, B> ap(__<__<__<__<T4.µ, S>, T>, U>, Function<A, B>> fn, __<__<__<__<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, Function<A, B>> fnQuadruple = asT4(fn);
        T4<S, T, U, A> aQuadruple = asT4(nestedA);
        return T4.of(getS().apply(fnQuadruple._1(), aQuadruple._1()),
                getT().apply(fnQuadruple._2(), aQuadruple._2()),
                getU().apply(fnQuadruple._3(), aQuadruple._3()),
                fnQuadruple._4().apply(aQuadruple._4()));

    }
}
