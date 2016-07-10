package org.highj.data.instance.either;

import org.highj.data.Either;
import org.highj.data.tuple.T2;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;

import static org.highj.data.ord.Ordering.GT;
import static org.highj.data.ord.Ordering.LT;

/**
 * An {@link Ord} instance for {@link Either}, which sorts Left and Right values according to the
 * given {@link Ord} instances, and assumes that Left values are smaller than Right values.
 *
 * @param <A> Left type
 * @param <B> Right type
 */
public interface EitherOrd<A, B> extends Ord<Either<A, B>> {
    T2<Ord<A>, Ord<B>> ords();

    @Override
    default Ordering cmp(Either<A, B> one, Either<A, B> two) {
        if (one == null && two == null) {
            return Ordering.EQ;
        } else if (one == null) {
            return Ordering.LT;
        } else if (two == null) {
            return Ordering.GT;
        } else if (one.isLeft() && two.isLeft()) {
            return ords()._1().cmp(one.getLeft(), two.getLeft());
        } else if (one.isRight() && two.isRight()) {
            return ords()._2().cmp(one.getRight(), two.getRight());
        } else {
            return one.isLeft() ? LT : GT;
        }
    }
}
