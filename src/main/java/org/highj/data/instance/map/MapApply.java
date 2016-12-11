package org.highj.data.instance.map;

import org.derive4j.hkt.__;
import org.highj.data.Map;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asMap;

/**
 * Note that this type-class isn't possible in Haskell, as highJ's Map has Object.hashCode and hence needs no Ord constraint.
 * @param <S> key type of the {@link Map}
 */
public interface MapApply<S> extends Apply<__<Map.µ, S>> {

    @Override
    default <A, B> Map<S, B> map(Function<A, B> fn, __<__<Map.µ, S>, A> nestedA) {
        return asMap(nestedA).map(fn);
    }

    @Override
    default <A, B> Map<S, B> ap(__<__<Map.µ, S>, Function<A, B>> fn, __<__<Map.µ, S>, A> nestedA) {
        Map<S, A> mapA = asMap(nestedA);
        Map<S, B> result = Map.of();
        for (T2<S, Function<A, B>> tuple : asMap(fn)) {
            for (A a : mapA.apply(tuple._1())) {
                result = result.plus(tuple._1(), tuple._2().apply(a));
            }
        }
        return result;
    }
}
