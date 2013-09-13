package org.highj.data.collection.either;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.MonadPlus;

public class EitherMonadPlus<S> implements EitherMonad<S>, MonadPlus<__.µ<Either.µ,S>> {

    public static enum Bias {FIRST_RIGHT, LAST_RIGHT}

    private final Monoid<S> monoid;
    private final Bias bias;

    public EitherMonadPlus(Monoid<S> monoid, Bias bias) {
        this.monoid = monoid;
        this.bias = bias;
    }

    @Override
    public <A> Either<S, A> mplus(_<__.µ<Either.µ, S>, A> one, _<__.µ<Either.µ, S>, A> two) {
        Either<S,A> first = Either.narrow(one);
        Either<S,A> second = Either.narrow(two);
        if (first.isLeft()) {
            return second.isRight() ? second :  Either.newLeft(monoid.dot(first.getLeft(), second.getLeft()));
        } else {
            return second.isLeft() || bias == Bias.FIRST_RIGHT ? first : second;
        }
    }

    @Override
    public <A> Either<S, A> mzero() {
        return Either.newLeft(monoid.identity());
    }
}
