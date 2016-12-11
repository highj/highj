package org.highj.data.instance.either3;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either3;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asEither3;

public interface Either3Bifunctor<S> extends Bifunctor<__<Either3.µ,S>> {
    @Override
    default <A, B, C> Either3<S, B, C> first(Function<A, B> fn, __2<__<Either3.µ, S>, A, C> nestedAC) {
        return asEither3(nestedAC).middleMap(fn);
    }

    @Override
    default <A, B, C> Either3<S, A, C> second(Function<B, C> fn, __2<__<Either3.µ, S>, A, B> nestedAB) {
        return asEither3(nestedAB).rightMap(fn);
    }
}
