package org.highj.data.instance.either;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asEither;

public interface EitherMonad<S> extends EitherFunctor<S>, Monad<__<Either.µ, S>>, MonadRec<__<Either.µ, S>> {
    @Override
    default <A> Either<S, A> pure(A a) {
        return Either.Right(a);
    }

    @Override
    default <A, B> Either<S, B> ap(__<__<Either.µ, S>, Function<A, B>> fn, __<__<Either.µ, S>, A> nestedA) {
        //a <*> b = do x <- a; y <- b; return (x y)
        return asEither(fn).<Either<S, B>>either(Either::Left,
                fnRight -> asEither(nestedA).<Either<S, B>>either(
                        Either::Left,
                        right -> Either.Right(fnRight.apply(right))));
    }

    @Override
    default <A, B> Either<S, B> bind(__<__<Either.µ, S>, A> a, Function<A, __<__<Either.µ, S>, B>> fn) {
        //Right$ m >>= k = k m
        //Left$ e  >>= __ = Left$ e
        return asEither(a).<Either<S,B>>either(Either::Left, right -> asEither(fn.apply(right)));
    }

    @Override
    default <A, B> Either<S, B> tailRec(Function<A, __<__<Either.µ, S>, Either<A, B>>> function, A startValue) {
        Either<S,Either<A, B>> step = Either.Right(Either.Left(startValue));
        while(step.isRight() && step.getRight().isLeft()) {
            step = asEither(function.apply(step.getRight().getLeft()));
        }
        return step.rightMap(Either::getRight);
    }
}
