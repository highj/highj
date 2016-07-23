package org.highj.data.instance.either3;

import org.highj.data.Either3;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T3;

import static org.highj.data.ord.Ordering.GT;
import static org.highj.data.ord.Ordering.LT;

/**
 * An {@link Ord} instance for {@link Either3}, which sorts Left, Middle and Right values according to the
 * given {@link Ord} instances, and assumes that Left values are smaller than Middle values,
 * and that Middle values are smaller than Right values.
 *
 * @param <A> the left type
 * @param <B> the middle type
 * @param <C> the right type
 */
public interface Either3Ord<A, B,C> extends Ord<Either3<A, B,C>> {
    T3<Ord<A>, Ord<B>, Ord<C>> ords();

    @Override
    default Ordering cmp(Either3<A, B, C> one, Either3<A, B, C> two) {
        if (one == null && two == null) {
            return Ordering.EQ;
        } else if (one == null) {
            return Ordering.LT;
        } else if (two == null) {
            return Ordering.GT;
        } else if (one.isLeft() && two.isLeft()) {
            return ords()._1().cmp(one.getLeft(), two.getLeft());
        } else if (one.isMiddle() && two.isMiddle()) {
            return ords()._2().cmp(one.getMiddle(), two.getMiddle());
        } else if (one.isRight() && two.isRight()) {
            return ords()._3().cmp(one.getRight(), two.getRight());
        } else if (one.isLeft()) {
            return LT;
        } else if (two.isLeft())  {
            return GT;
        } else {
            return (one.isMiddle()) ? LT : GT;
        }
    }
}
