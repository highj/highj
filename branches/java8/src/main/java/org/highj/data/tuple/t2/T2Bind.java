package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface T2Bind<S> extends T2Apply<S>, Bind<__.µ<T2.µ, S>> {

    @Override
    public default <A, B> _<__.µ<T2.µ, S>, B> bind(_<__.µ<T2.µ, S>, A> nestedA, Function<A, _<__.µ<T2.µ, S>, B>> fn) {
        T2<S, A> ta = Tuple.narrow2(nestedA);
        T2<S, B> tb = Tuple.narrow2(fn.apply(ta._2()));
        return Tuple.of(getS().dot(ta._1(), tb._1()), tb._2());
    }
}
