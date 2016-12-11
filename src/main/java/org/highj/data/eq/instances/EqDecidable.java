package org.highj.data.eq.instances;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.eq.Eq;
import org.highj.typeclass1.contravariant.Decidable;

import java.util.function.Function;

import static org.highj.Hkt.asEq;

public interface EqDecidable extends EqDivisible, Decidable<Eq.µ> {

    @Override
    default <A> Eq<A> lose(Function<A, Void> fn) {
        throw new AssertionError("absurd");
    }

    @Override
    default <A, B, C> Eq<A> choose(Function<A, Either<B, C>> fn, __<Eq.µ, B> fb, __<Eq.µ, C> fc) {
        return (x, y) -> fn.apply(x).either(
                c -> fn.apply(y).either(
                        d -> asEq(fb).eq(c, d),
                        otherwise -> false
                ),
                c -> fn.apply(y).either(
                        otherwise -> false,
                        d -> asEq(fc).eq(c, d)
                ));
    }
}
