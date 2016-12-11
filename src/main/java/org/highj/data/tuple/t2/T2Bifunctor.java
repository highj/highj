package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asT2;

public interface T2Bifunctor extends Bifunctor<T2.µ> {

    default <A, B, C, D> T2<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<T2.µ, A, C> nestedAC) {
        return asT2(nestedAC).bimap(fn1, fn2);
    }


    default <A, B, C> T2<B, C> first(Function<A, B> fn, __2<T2.µ, A, C> nestedAC) {
        return asT2(nestedAC).map_1(fn);
    }

    default <A, B, C> T2<A, C> second(Function<B, C> fn, __2<T2.µ, A, B> nestedAB) {
        return asT2(nestedAB).map_2(fn);
    }
}
