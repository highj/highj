package org.highj.data.collection.map;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Map;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * Note that this type-class isn't possible in Haskell, as highJ's Map has Object.hashCode and hence needs no Ord constraint.
 * @param <S>
 */
public interface MapApply<S> extends Apply<__.µ<Map.µ, S>> {

    @Override
    public default <A, B> Map<S, B> map(Function<A, B> fn, _<__.µ<Map.µ, S>, A> nestedA) {
        return Map.narrow(nestedA).map(fn);
    }

    @Override
    public default <A, B> Map<S, B> ap(_<__.µ<Map.µ, S>, Function<A, B>> fn, _<__.µ<Map.µ, S>, A> nestedA) {
        Map<S, A> mapA = Map.narrow(nestedA);
        Map<S, B> result = Map.of();
        for (T2<S, Function<A, B>> tuple : Map.narrow(fn)) {
            for (A a : mapA.apply(tuple._1())) {
                result = result.plus(tuple._1(), tuple._2().apply(a));
            }
        }
        return result;
    }
}
