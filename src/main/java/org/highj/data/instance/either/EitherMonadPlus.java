package org.highj.data.instance.either;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.MonadPlus;

public interface EitherMonadPlus<S> extends EitherMonad<S>, MonadPlus<__<Either.µ,S>> {

    Monoid<S> monoid();
    Bias bias();

    @Override
    default <A> Either<S, A> mplus(__<__<Either.µ, S>, A> one, __<__<Either.µ, S>, A> two) {
        Either<S,A> first = Either.narrow(one);
        Either<S,A> second = Either.narrow(two);
        if (first.isLeft()) {
            return second.isRight() ? second :  Either.Left(monoid().apply(first.getLeft(), second.getLeft()));
        } else if (second.isLeft()) {
            return first;
        } else {
            return bias().select(first,second);
        }
    }

    @Override
    default <A> Either<S, A> mzero() {
        return Either.Left(monoid().identity());
    }
}
