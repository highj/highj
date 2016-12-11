package org.highj.data.instance.either4;

import org.derive4j.hkt.__;
import org.highj.data.Either4;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asEither4;

public interface Either4Applicative<S, T, U> extends Either4Functor<S, T, U>,
        Applicative<__<__<__<Either4.µ, S>, T>,U>> {
    @Override
    default <A> Either4<S, T, U, A> pure(A a) {
        return Either4.E4(a);
    }

    @Override
    default <A, B> Either4<S, T, U, B> ap(__<__<__<__<Either4.µ, S>, T>, U>, Function<A, B>> fn,
                                          __<__<__<__<Either4.µ, S>, T>, U>, A> nestedA) {
        return asEither4(fn).either(
                Either4::E1,
                Either4::E2,
                Either4::E3,
                fn4 -> asEither4(nestedA).either(
                        Either4::E1,
                        Either4::E2,
                        Either4::E3,
                        a -> Either4.E4(fn4.apply(a))));
    }
}
