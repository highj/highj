package org.highj.data.eq.instances;

import org.derive4j.hkt.__;
import org.highj.data.eq.Eq;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.Hkt.asEq;

public interface EqDivisible extends EqContravariant, Divisible<Eq.µ> {

    @Override
    default <A, B, C> Eq<A> divide(Function<A, T2<B, C>> fn, __<Eq.µ, B> fb, __<Eq.µ, C> fc) {
        return (x, y) -> T2.merge(fn.apply(x), fn.apply(y),
                (bx, by) -> asEq(fb).eq(bx, by),
                (cx, cy) -> asEq(fc).eq(cx, cy))
                .cata((one, two) -> one && two);
    }

    @Override
    default <A> Eq<A> conquer() {
        return (x, y) -> true;
    }
}
