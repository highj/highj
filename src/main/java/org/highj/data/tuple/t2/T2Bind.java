package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

import static org.highj.Hkt.asT2;

public interface T2Bind<S> extends T2Apply<S>, Bind<__<T2.µ, S>> {

    @Override
    default <A, B> T2<S, B> bind(__<__<T2.µ, S>, A> nestedA, Function<A, __<__<T2.µ, S>, B>> fn) {
        T2<S, A> ta = asT2(nestedA);
        T2<S, B> tb = asT2(fn.apply(ta._2()));
        return T2.of(get().apply(ta._1(), tb._1()), tb._2());
    }
}
