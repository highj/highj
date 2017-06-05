package org.highj.data.structural.constant;

import org.derive4j.hkt.__2;
import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.Const;

public interface ConstBifunctor extends Bifunctor<Const.µ> {

    @Override
    default <S, S1, A, A1> Const<S1, A1> bimap(Function<S, S1> fn1, Function<A, A1> fn2, __2<Const.µ, S, A> sa) {
        return Const(fn1.apply(asConst(sa).get()));
    }
}
