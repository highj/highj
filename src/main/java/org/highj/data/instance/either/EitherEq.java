package org.highj.data.instance.either;

import org.highj.data.Either;
import org.highj.data.eq.Eq;
import org.highj.data.tuple.T2;

/**
 * An {@link Eq} instance for {@link Either}.
 *
 * @param <A> Left type
 * @param <B> Right type
 */
public interface EitherEq<A,B> extends Eq<Either<A,B>> {

    T2<Eq<A>,Eq<B>> eqs();

    @Override
    default boolean eq(Either<A, B> one, Either<A, B> two) {
        if (one == null && two == null) {
            return true;
        } else if (one == null || two == null) {
            return false;
        } else if (one.isLeft() && two.isLeft()) {
            return eqs()._1().eq(one.getLeft(), two.getLeft());
        } else {
            return one.isRight() && two.isRight() && eqs()._2().eq(one.getRight(), two.getRight());
        }
    }
}
