package org.highj.data.instance.either4;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either3;
import org.highj.data.Either4;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asEither4;

public interface Either4Bifunctor<S, T> extends Bifunctor<__<__<Either4.µ,S>,T>> {
    @Override
    default <A, B, C> Either4<S, T, B, C> first(Function<A, B> fn,
                                                __2<__<__<Either4.µ, S>,T>, A, C> nestedAC) {
        return asEither4(nestedAC).map3(fn);
    }

    @Override
    default <A, B, C> Either4<S, T, A, C> second(Function<B, C> fn,
                                                 __2<__<__<Either4.µ, S>,T>, A, B> nestedAB) {
        return asEither4(nestedAB).map4(fn);
    }
}
