package org.highj.data.instance.either3;

import org.highj.data.Either3;
import org.highj.data.eq.Eq;
import org.highj.data.tuple.T3;

/**
 * An {@link Eq} instance for {@link Either3}.
 *
 * @param <A> the left type
 * @param <B> the middle type
 * @param <C> the right type
 */
public interface Either3Eq<A,B,C> extends Eq<Either3<A,B,C>> {

    T3<Eq<A>,Eq<B>, Eq<C>> eqs();

    @Override
    default boolean eq(Either3<A, B, C> one, Either3<A, B, C> two) {
        if (one == null && two == null) {
            return true;
        } else if (one == null || two == null) {
            return false;
        } else if (one.isLeft() && two.isLeft()) {
            return eqs()._1().eq(one.getLeft(), two.getLeft());
        } else if (one.isMiddle() && two.isMiddle()) {
            return eqs()._2().eq(one.getMiddle(), two.getMiddle());
        } else {
            return one.isRight() && two.isRight() && eqs()._3().eq(one.getRight(), two.getRight());
        }
    }
}
