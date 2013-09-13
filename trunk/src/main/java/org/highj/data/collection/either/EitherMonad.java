package org.highj.data.collection.either;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface EitherMonad<S> extends EitherFunctor<S>, Monad<__.µ<Either.µ, S>> {
    @Override
    public default <A> Either<S, A> pure(A a) {
        return Either.newRight(a);
    }

    @Override
    public default <A, B> Either<S, B> ap(_<__.µ<Either.µ, S>, Function<A, B>> fn, _<__.µ<Either.µ, S>, A> nestedA) {
        //a <*> b = do x <- a; y <- b; return (x y)
        return Either.narrow(fn).<Either<S, B>>either(Either::newLeft,
                fnRight -> Either.narrow(nestedA).<Either<S, B>>either(
                        Either::newLeft,
                        right -> Either.newRight(fnRight.apply(right))));
    }

    @Override
    public default <A, B> Either<S, B> bind(_<__.µ<Either.µ, S>, A> a, Function<A, _<__.µ<Either.µ, S>, B>> fn) {
        //lazyRight m >>= k = k m
        //lazyLeft e  >>= _ = lazyLeft e
        return Either.narrow(a).<Either<S,B>>either(Either::newLeft, right -> Either.narrow(fn.apply(right)));
    }
}
