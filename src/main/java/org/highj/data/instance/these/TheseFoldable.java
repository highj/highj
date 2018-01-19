package org.highj.data.instance.these;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;

public interface TheseFoldable<F> extends Foldable<__<These.µ, F>> {
    @Override
    default <A, B> B foldr(final Function<A, Function<B, B>> fn, B start, __<__<These.µ, F>, A> as) {
        return Hkt.asThese(as).these(
                a -> start,
                b -> fn.apply(b).apply(start),
                (a, b) -> fn.apply(b).apply(start)
        );
    }
}
