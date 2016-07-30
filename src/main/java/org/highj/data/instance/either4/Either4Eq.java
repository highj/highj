package org.highj.data.instance.either4;

import org.highj.data.Either3;
import org.highj.data.Either4;
import org.highj.data.eq.Eq;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;

/**
 * An {@link Eq} instance for {@link Either4}.
 *
 * @param <A> the E1 type
 * @param <B> the E2 type
 * @param <C> the E3 type
 * @param <D> the E4 type
 */
public interface Either4Eq<A,B,C,D> extends Eq<Either4<A,B,C,D>> {

    T4<Eq<A>,Eq<B>, Eq<C>, Eq<D>> eqs();

    @Override
    default boolean eq(Either4<A, B, C, D> one, Either4<A, B, C, D> two) {
        if (one == null && two == null) {
            return true;
        } else if (one == null || two == null) {
            return false;
        } else if (one.isE1() && two.isE1()) {
            return eqs()._1().eq(one.getE1(), two.getE1());
        } else if (one.isE2() && two.isE2()) {
            return eqs()._2().eq(one.getE2(), two.getE2());
        } else if (one.isE3() && two.isE3()) {
            return eqs()._3().eq(one.getE3(), two.getE3());
        } else {
            return one.isE4() && two.isE4() && eqs()._4().eq(one.getE4(), two.getE4());
        }
    }
}
