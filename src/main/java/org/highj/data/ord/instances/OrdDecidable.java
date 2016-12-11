package org.highj.data.ord.instances;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.typeclass1.contravariant.Decidable;

import java.util.function.Function;

import static org.highj.Hkt.asOrd;

public interface OrdDecidable extends OrdDivisible, Decidable<Ord.µ> {

    @Override
    default <A> Ord<A> lose(Function<A, Void> fn) {
        throw new AssertionError("absurd");
    }

    @Override
    default <A, B, C> Ord<A> choose(Function<A, Either<B, C>> fn, __<Ord.µ, B> fb, __<Ord.µ, C> fc) {
        return (x, y) -> fn.apply(x).either(
                c -> fn.apply(y).either(
                        d -> asOrd(fb).cmp(c, d),
                        otherwise -> Ordering.LT
                ),
                c -> fn.apply(y).either(
                        otherwise -> Ordering.GT,
                        d -> asOrd(fc).cmp(c, d)
                ));
    }
}
