package org.highj.data.instance.intmap;

import org.derive4j.hkt.__;
import org.highj.data.IntMap;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asIntMap;

public interface IntMapFunctor extends Functor<IntMap.µ> {
    @Override
    default <A, B> IntMap<B> map(Function<A, B> fn, __<IntMap.µ, A> nestedA) {
        return asIntMap(nestedA).mapValues(fn);
    }
}
