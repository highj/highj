package org.highj.data.ord.instances;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.Hkt.asOrd;
import static org.highj.data.ord.Ordering.EQ;

public interface OrdDivisible extends OrdContravariant, Divisible<Ord.µ> {

    @Override
    default  <A, B, C> Ord<A> divide(Function<A, T2<B, C>> fn, __<Ord.µ, B> fb, __<Ord.µ, C> fc) {
        return (x, y) -> T2.merge(fn.apply(x), fn.apply(y),
                (bx, by) -> asOrd(fb).cmp(bx, by),
                (cx, cy) -> asOrd(fc).cmp(cx, cy))
                .cata(Ordering::andThen);
    }

    @Override
    default  <A> Ord<A> conquer() {
        return (x, y) -> EQ;
    }
}
