package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.Const;
import static org.highj.data.structural.Const.µ;

public interface ConstApply<S> extends Apply<__<µ, S>>, ConstFunctor<S> {

    Semigroup<S> getS();

    @Override
    default <A, B> Const<S, B> ap(__<__<µ, S>, Function<A, B>> fn, __<__<µ, S>, A> nestedA) {
        S s1 = asConst(fn).get();
        S s2 = asConst(nestedA).get();
        return Const(getS().apply(s1, s2));
    }
}
