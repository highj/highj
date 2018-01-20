package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifoldable.Bifoldable;

import java.util.function.Function;

public interface T2Bifoldable extends Bifoldable<T2.µ> {

    @Override
    default <M, A, B> M bifoldMap(Monoid<M> monoid, Function<A, M> fn1, Function<B, M> fn2, __2<T2.µ, A, B> nestedAB) {
        T2<A,B> ab = Hkt.asT2(nestedAB);
        return monoid.apply(fn1.apply(ab._1()), fn2.apply(ab._2()));
    }
}
