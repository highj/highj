package org.highj.data.collection.either;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface EitherFunctor<S> extends Functor<__.µ<Either.µ, S>> {

    @Override
    public default <A, B> Either<S, B> map(Function<A, B> fn, _<__.µ<Either.µ, S>, A> nestedA) {
        return Either.narrow(nestedA).rightMap(fn);
    }
}
