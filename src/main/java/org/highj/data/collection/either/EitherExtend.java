package org.highj.data.collection.either;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.typeclass1.comonad.Extend;

public interface EitherExtend<S> extends EitherFunctor<S>, Extend<_<Either.µ, S>> {
    @Override
    default <A> Either<S, _<_<Either.µ, S>, A>> duplicate(_<_<Either.µ, S>, A> nestedA) {
        //duplicated (Left a) = Left a
        //duplicated r = Right r
        return Either.narrow(nestedA).rightMap(Either::newRight);
    }
}
