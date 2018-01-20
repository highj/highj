package org.highj.data.structural.constant;

import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.bifoldable.Bifoldable;

import java.util.function.Function;

public interface ConstBifoldable extends Bifoldable<Const.µ> {

    @Override
    default <M, A, B> M bifoldMap(Monoid<M> monoid, Function<A, M> fn1, Function<B, M> fn2, __2<Const.µ, A, B> nestedAB) {
        return fn1.apply(Hkt.asConst(nestedAB).get());
    }
}
