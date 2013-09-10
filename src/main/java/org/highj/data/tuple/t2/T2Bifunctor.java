package org.highj.data.tuple.t2;

import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface T2Bifunctor extends Bifunctor<T2.µ> {

    public default <A, B, C, D> T2<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<T2.µ, A, C> nestedAC) {
        T2<A, C> pair = T2.narrow(nestedAC);
        return T2.of(fn1.apply(pair._1()), fn2.apply(pair._2()));
    }


    public default <A, B, C> T2<B, C> first(Function<A, B> fn, __<T2.µ, A, C> nestedAC) {
        return T2.narrow(nestedAC).map_1(fn);
    }

    public default <A, B, C> T2<A, C> second(Function<B, C> fn, __<T2.µ, A, B> nestedAB) {
        return T2.narrow(nestedAB).map_2(fn);
    }
}
