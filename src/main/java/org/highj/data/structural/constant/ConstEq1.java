package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.structural.Const;

public interface ConstEq1<S> extends Eq1<__<Const.µ, S>> {

    Eq<? super S> eqS();

    @Override
    default <T> Eq<__<__<Const.µ, S>, T>> eq1(Eq<? super T> ignored) {
        return (one, two) -> {
            Const<S, T> first = Hkt.asConst(one);
            Const<S, T> second = Hkt.asConst(two);
            return eqS().eq(first.get(), second.get());
        };
    }
}
