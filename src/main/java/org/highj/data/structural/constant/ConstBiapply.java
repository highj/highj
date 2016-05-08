package org.highj.data.structural.constant;

import org.derive4j.hkt.__2;
import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

public interface ConstBiapply extends ConstBifunctor, Biapply<Const.µ> {

    @Override
    public default <A, B, C, D> __2<Const.µ, B, D> biapply(__2<Const.µ, Function<A, B>, Function<C, D>> fn, __2<Const.µ, A, C> ac) {
        Const<Function<A,B>, Function<C,D>> constFn = Const.narrow(fn);
        Const<A,C> constAc = Const.narrow(ac);
        return new Const<>(constFn.get().apply(constAc.get()));
    }
}
