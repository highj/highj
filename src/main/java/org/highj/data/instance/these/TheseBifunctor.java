package org.highj.data.instance.these;

import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface TheseBifunctor extends Bifunctor<These.µ> {

    @Override
    default <A, B, C, D> These<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __2<These.µ, A, C> nestedAC) {
        return Hkt.asThese(nestedAC).mapThese(fn1, fn2);
    }

    @Override
    default <A, B, C> These<B, C> first(Function<A, B> fn, __2<These.µ, A, C> nestedAC) {
        return Hkt.asThese(nestedAC).mapThis(fn);
    }

    @Override
    default <A, B, C> These<A, C> second(Function<B, C> fn, __2<These.µ, A, B> nestedAB) {
        return Hkt.asThese(nestedAB).mapThat(fn);
    }
}
