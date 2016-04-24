package org.highj.data.collection.either;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

public interface EitherMonadPlus<S> extends EitherMonad<S>, MonadPlus<_<Either.µ,S>> {

    Monoid<S> monoid();
    Bias bias();

    @Override
    default <A> Either<S, A> mplus(_<_<Either.µ, S>, A> one, _<_<Either.µ, S>, A> two) {
        Either<S,A> first = Either.narrow(one);
        Either<S,A> second = Either.narrow(two);
        if (first.isLeft()) {
            return second.isRight() ? second :  Either.newLeft(monoid().apply(first.getLeft(), second.getLeft()));
        } else if (second.isLeft()) {
            return first;
        } else {
            return bias().select(first,second);
        }
    }

    @Override
    default <A> Either<S, A> mzero() {
        return Either.newLeft(monoid().identity());
    }
}
