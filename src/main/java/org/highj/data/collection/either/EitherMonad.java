package org.highj.data.collection.either;

import org.derive4j.hkt.__;
import org.highj.data.collection.Either;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

public interface EitherMonad<S> extends EitherFunctor<S>, Monad<__<Either.µ, S>>, MonadRec<__<Either.µ, S>> {
    @Override
    default <A> Either<S, A> pure(A a) {
        return Either.newRight(a);
    }

    @Override
    default <A, B> Either<S, B> ap(__<__<Either.µ, S>, Function<A, B>> fn, __<__<Either.µ, S>, A> nestedA) {
        //a <*> b = do x <- a; y <- b; return (x y)
        return Either.narrow(fn).<Either<S, B>>either(Either::newLeft,
                fnRight -> Either.narrow(nestedA).<Either<S, B>>either(
                        Either::newLeft,
                        right -> Either.newRight(fnRight.apply(right))));
    }

    @Override
    default <A, B> Either<S, B> bind(__<__<Either.µ, S>, A> a, Function<A, __<__<Either.µ, S>, B>> fn) {
        //lazyRight m >>= k = k m
        //lazyLeft e  >>= __ = lazyLeft e
        return Either.narrow(a).<Either<S,B>>either(Either::newLeft, right -> Either.narrow(fn.apply(right)));
    }

    @Override
    default <A, B> Either<S, B> tailRec(Function<A, __<__<Either.µ, S>, Either<A, B>>> function, A startValue) {
        Either<S,Either<A, B>> step = Either.newRight(Either.newLeft(startValue));
        while(step.isRight() && step.getRight().isLeft()) {
            step = Either.narrow(function.apply(step.getRight().getLeft()));
        }
        return step.rightMap(Either::getRight);
    }
}
