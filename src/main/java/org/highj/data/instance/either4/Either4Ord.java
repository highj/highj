package org.highj.data.instance.either4;

import org.highj.data.Either4;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T4;

/**
 * An {@link Ord} instance for {@link Either4}, which sorts E1, E2, E3 and E4 values according to the
 * given {@link Ord} instances, and assumes that E1 values are smaller than E2 values etc.
 *
 * @param <A> the E1 type
 * @param <B> the E2 type
 * @param <C> the E3 type
 * @param <D> the E4 type
 */
public interface Either4Ord<A, B, C, D> extends Ord<Either4<A, B, C, D>> {
    T4<Ord<A>, Ord<B>, Ord<C>, Ord<D>> ords();

    @Override
    default Ordering cmp(Either4<A, B, C, D> one, Either4<A, B, C, D> two) {
        if (one == null && two == null) {
            return Ordering.EQ;
        } else if (one == null) {
            return Ordering.LT;
        } else if (two == null) {
            return Ordering.GT;
        } else if (one.isE1() && two.isE1()) {
            return ords()._1().cmp(one.getE1(), two.getE1());
        } else if (one.isE2() && two.isE2()) {
            return ords()._2().cmp(one.getE2(), two.getE2());
        } else if (one.isE3() && two.isE3()) {
            return ords()._3().cmp(one.getE3(), two.getE3());
        } else if (one.isE4() && two.isE4()) {
            return ords()._4().cmp(one.getE4(), two.getE4());
        } else {
            return Ordering.compare(one.index(), two.index());
        }
    }
}
