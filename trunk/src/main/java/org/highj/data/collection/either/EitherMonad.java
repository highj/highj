package org.highj.data.collection.either;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class EitherMonad<S> extends EitherFunctor<S> implements Monad<__.µ<Either.µ, S>> {
    @Override
    public <A> _<__.µ<Either.µ, S>, A> pure(A a) {
        return Either.Right(a);
    }

    @Override
    public <A, B> _<__.µ<Either.µ, S>, B> ap(_<__.µ<Either.µ, S>, Function<A, B>> fn, _<__.µ<Either.µ, S>, A> nestedA) {
        //a <*> b = do x <- a; y <- b; return (x y)
        return Either.narrow(fn).<Either<S, B>>either(Either::Left,
                fnRight -> Either.narrow(nestedA).<Either<S, B>>either(
                        Either::Left,
                        right -> Either.Right(fnRight.apply(right))));
    }

    @Override
    public <A, B> _<__.µ<Either.µ, S>, B> bind(_<__.µ<Either.µ, S>, A> a, Function<A, _<__.µ<Either.µ, S>, B>> fn) {
        //RightLazy m >>= k = k m
        //LeftLazy e  >>= _ = LeftLazy e
        return Either.narrow(a).<_<__.µ<Either.µ, S>, B>>either(Either::Left, fn::apply);
    }
}
