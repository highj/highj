package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asT2;

public interface T2Apply<S> extends T2Functor<S>, Apply<__<T2.µ, S>> {

    Semigroup<S> get();

    @Override
    default <A, B> T2<S, B> ap(__<__<T2.µ, S>, Function<A, B>> fn, __<__<T2.µ, S>, A> nestedA) {
        T2<S, Function<A, B>> fnPair = asT2(fn);
        T2<S, A> aPair = asT2(nestedA);
        return T2.of(get().apply(fnPair._1(), aPair._1()), fnPair._2().apply(aPair._2()));
    }

}
