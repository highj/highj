package org.highj.typeclass0.compare.ord;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.compare.Ordering;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.typeclass0.compare.Ordering.EQ;

public abstract class OrdDivisible implements OrdContravariant, Divisible<Ord.µ> {

    @Override
    public <A, B, C> Ord<A> divide(Function<A, T2<B, C>> fn, __<Ord.µ, B> fb, __<Ord.µ, C> fc) {
        return (x, y) -> T2.merge(fn.apply(x), fn.apply(y),
                bx -> by -> Ord.narrow(fb).cmp(bx, by),
                cx -> cy -> Ord.narrow(fc).cmp(cx, cy))
                .cata(Ordering::andThen);
    }

    @Override
    public <A> Ord<A> conquer() {
        return (x, y) -> EQ;
    }
}
