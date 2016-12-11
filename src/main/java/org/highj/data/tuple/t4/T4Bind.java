package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T4;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asT4;

public interface T4Bind<S, T, U> extends T4Apply<S, T, U>, Bind<__<__<__<T4.µ, S>, T>, U>> {
    @Override
    default <A, B> T4<S, T, U, B> bind(
            __<__<__<__<T4.µ, S>, T>, U>, A> nestedA,
            Function<A, __<__<__<__<T4.µ, S>, T>, U>, B>> fn) {
        T4<S, T, U, A> ta = asT4(nestedA);
        T4<S, T, U, B> tb = asT4(fn.apply(ta._4()));
        return T4.of(getS().apply(ta._1(), tb._1()),
                getT().apply(ta._2(), tb._2()),
                getU().apply(ta._3(), tb._3()),
                tb._4());
    }
}
