package org.highj.data.collection.either;

import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface EitherBifunctor extends Bifunctor<Either.µ> {
    @Override
    public default <A, B, C, D> Either<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<Either.µ, A, C> nestedAC) {
        Either<A,C> either = Either.narrow(nestedAC);
        return either.bimap(fn1, fn2);
    }
}
