package org.highj.data.instance.either4;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either3;
import org.highj.data.Either4;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface Either4Bifunctor<S, T> extends Bifunctor<__<__<Either4.µ,S>,T>> {
    @Override
    default <A, B, C> Either4<S, T, B, C> first(Function<A, B> fn,
                                                __2<__<__<Either4.µ, S>,T>, A, C> nestedAC) {
        return Either4.narrow(nestedAC).map3(fn);
    }

    @Override
    default <A, B, C> Either4<S, T, A, C> second(Function<B, C> fn,
                                                 __2<__<__<Either4.µ, S>,T>, A, B> nestedAB) {
        return Either4.narrow(nestedAB).map4(fn);
    }
}
