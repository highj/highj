package org.highj.data.structural.constant;

import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface ConstBifunctor extends Bifunctor<Const.µ> {

    @Override
    public default <A, B, C, D> Const<B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<Const.µ, A, C> nestedAC) {
        Const<A,C> constant = Const.narrow(nestedAC);
        return new Const<>(fn1.apply(constant.get()));
    }
}
