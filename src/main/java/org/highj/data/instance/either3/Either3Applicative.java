package org.highj.data.instance.either3;

import org.derive4j.hkt.__;
import org.highj.data.Either3;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asEither3;

public interface Either3Applicative<S, T> extends Either3Functor<S, T>, Applicative<__<__<Either3.µ, S>, T>> {
    @Override
    default <A> Either3<S, T, A> pure(A a) {
        return Either3.Right(a);
    }

    @Override
    default <A, B> Either3<S, T, B> ap(__<__<__<Either3.µ, S>, T>, Function<A, B>> fn, __<__<__<Either3.µ, S>, T>, A> nestedA) {
        return asEither3(fn).either(
                Either3::Left,
                Either3::Middle,
                fnRight -> asEither3(nestedA).either(
                        Either3::Left,
                        Either3::Middle,
                        right -> Either3.Right(fnRight.apply(right))));
    }
}
