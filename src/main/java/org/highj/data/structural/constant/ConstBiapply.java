package org.highj.data.structural.constant;

import org.derive4j.hkt.__2;
import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Biapply;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.Const;

public interface ConstBiapply extends ConstBifunctor, Biapply<Const.µ> {

    @Override
    default <S, S1, A, A1> Const<S1, A1> biapply(__2<Const.µ, Function<S, S1>, Function<A, A1>> fn, __2<Const.µ, S, A> sa) {
        Const<Function<S, S1>, Function<A, A1>> constFn = asConst(fn);
        return Const(constFn.get().apply(asConst(sa).get()));
    }
}
