package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface T2Apply<S> extends T2Functor<S>, Apply<__.µ<T2.µ, S>> {

    public Semigroup<S> getS();

    @Override
    public default <A, B> _<__.µ<T2.µ, S>, B> ap(_<__.µ<T2.µ, S>, Function<A, B>> fn, _<__.µ<T2.µ, S>, A> nestedA) {
        T2<S, Function<A, B>> fnPair = Tuple.narrow2(fn);
        T2<S, A> aPair = Tuple.narrow2(nestedA);
        return Tuple.of(getS().dot(fnPair._1(), aPair._1()), fnPair._2().apply(aPair._2()));
    }

}
