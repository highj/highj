package org.highj.data.instance.either3;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Either3;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asEither3;

public interface Either3Monad<S,T> extends Either3Applicative<S,T>, MonadRec<__<__<Either3.µ,S>,T>> {

    @Override
    default <A, B> Either3<S, T, B> bind(__<__<__<Either3.µ, S>, T>, A> nestedA, Function<A, __<__<__<Either3.µ, S>, T>, B>> fn) {
        return asEither3(nestedA).either(
                Either3::Left, Either3::Middle, right -> asEither3(fn.apply(right)));
    }

    @Override
    default <A, B> Either3<S, T, B> tailRec(Function<A, __<__<__<Either3.µ, S>, T>, Either<A, B>>> function, A startValue) {
        Either3<S, T, Either<A, B>> step = Either3.Right(Either.Left(startValue));
        while(step.isRight() && step.getRight().isLeft()) {
            step = asEither3(function.apply(step.getRight().getLeft()));
        }
        return step.rightMap(Either::getRight);
    }
}
