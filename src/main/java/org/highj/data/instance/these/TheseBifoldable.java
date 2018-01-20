package org.highj.data.instance.these;

import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.These;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifoldable.Bifoldable;

import java.util.function.BiFunction;

public interface TheseBifoldable extends Bifoldable<These.µ> {
    @Override
    default <M> M bifold(Monoid<M> monoid, __2<These.µ, M, M> nestedM) {
        return Hkt.asThese(nestedM).these(
                a -> a,
                b -> b,
                monoid);
    }

    @Override
    default <A, B, C> C bifoldr(BiFunction<A, C, C> fn1, BiFunction<B, C, C> fn2, C start, __2<These.µ, A, B> nestedAB) {
        return Hkt.asThese(nestedAB).these(
                a -> fn1.apply(a, start),
                b -> fn2.apply(b, start),
                (a, b) -> fn2.apply(b, fn1.apply(a, start)));
    }

    @Override
    default <A, B, C> C bifoldl(BiFunction<C, A, C> fn1, BiFunction<C, B, C> fn2, C start, __2<These.µ, A, B> nestedAB) {
        return Hkt.asThese(nestedAB).these(
                a -> fn1.apply(start, a),
                b -> fn2.apply(start, b),
                (a, b) -> fn2.apply(fn1.apply(start, a), b));
    }
}
