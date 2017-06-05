package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.Const;
import static org.highj.data.structural.Const.µ;

public interface ConstFunctor<S> extends Functor<__<µ, S>> {
    @Override
    default <A, B> Const<S, B> map(Function<A, B> fn, __<__<µ, S>, A> nestedA) {
        return Const(asConst(nestedA).get());
    }
}
