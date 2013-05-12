package org.highj.data.structural.constant;

import org.highj._;
import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.data.structural.Const.narrow;
import static org.highj.data.structural.Const.µ;

public interface ConstApply<S> extends Apply<__.µ<µ,S>>, ConstFunctor<S> {

    public Semigroup<S> getS();

    @Override
    public default <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, Function<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
        S s1 = narrow(fn).get();
        S s2 = narrow(nestedA).get();
        return new Const<>(getS().dot(s1, s2));
    }

}
