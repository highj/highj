package org.highj.data.collection.either;

import org.derive4j.hkt.__;
import org.highj.data.collection.Either;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

interface EitherFunctor<S> extends Functor<__<Either.µ, S>> {

    @Override
    default <A, B> Either<S, B> map(Function<A, B> fn, __<__<Either.µ, S>, A> nestedA) {
        return Either.narrow(nestedA).rightMap(fn);
    }
}
