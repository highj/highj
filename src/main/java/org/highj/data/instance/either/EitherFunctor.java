package org.highj.data.instance.either;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asEither;

interface EitherFunctor<S> extends Functor<__<Either.µ, S>> {

    @Override
    default <A, B> Either<S, B> map(Function<A, B> fn, __<__<Either.µ, S>, A> nestedA) {
        return asEither(nestedA).rightMap(fn);
    }
}
