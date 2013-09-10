package org.highj.data.structural.constant;

import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

public interface ConstBiapply extends ConstBifunctor, Biapply<Const.µ> {

    @Override
    public default <A, B, C, D> __<Const.µ, B, D> biapply(__<Const.µ, Function<A, B>, Function<C, D>> fn, __<Const.µ, A, C> ac) {
        Const<Function<A,B>, Function<C,D>> constFn = Const.narrow(fn);
        Const<A,C> constAc = Const.narrow(ac);
        return new Const<>(constFn.get().apply(constAc.get()));
    }
}
