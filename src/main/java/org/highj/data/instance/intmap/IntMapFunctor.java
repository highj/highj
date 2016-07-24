package org.highj.data.instance.intmap;

import org.derive4j.hkt.__;
import org.highj.data.IntMap;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface IntMapFunctor extends Functor<IntMap.µ> {
    @Override
    default <A, B> IntMap<B> map(Function<A, B> fn, __<IntMap.µ, A> nestedA) {
        return IntMap.narrow(nestedA).mapValues(fn);
    }
}
