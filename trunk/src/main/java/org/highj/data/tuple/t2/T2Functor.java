package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T2Functor<S> extends Functor<__.µ<T2.µ, S>> {

    @Override
    public default <A, B> T2<S, B> map(Function<A, B> fn, _<__.µ<T2.µ, S>, A> nestedA) {
        return Tuple.narrow2(nestedA).map_2(fn);
    }
}
