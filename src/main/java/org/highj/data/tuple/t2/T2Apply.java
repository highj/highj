package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface T2Apply<S> extends T2Functor<S>, Apply<__.µ<T2.µ, S>> {

    public Semigroup<S> getS();

    @Override
    public default <A, B> T2<S, B> ap(_<__.µ<T2.µ, S>, Function<A, B>> fn, _<__.µ<T2.µ, S>, A> nestedA) {
        T2<S, Function<A, B>> fnPair = T2.narrow(fn);
        T2<S, A> aPair = T2.narrow(nestedA);
        return T2.of(getS().dot(fnPair._1(), aPair._1()), fnPair._2().apply(aPair._2()));
    }

}
