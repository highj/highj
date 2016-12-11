package org.highj.data.instance.either;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.typeclass1.comonad.Extend;

import static org.highj.Hkt.asEither;

public interface EitherExtend<S> extends EitherFunctor<S>, Extend<__<Either.µ, S>> {
    @Override
    default <A> Either<S, __<__<Either.µ, S>, A>> duplicate(__<__<Either.µ, S>, A> nestedA) {
        //duplicated (Left a) = Left a
        //duplicated r = Right r
        return asEither(nestedA).rightMap(Either::Right);
    }
}
