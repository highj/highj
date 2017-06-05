package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.Hkt.asConst;
import static org.highj.data.structural.Const.Const;
import static org.highj.data.structural.Const.µ;

public interface ConstDivisible<S> extends ConstContravariant<S>, Divisible<__<µ, S>> {

    Monoid<S> getS();

    @Override
    default <A, B, C> Const<S, A> divide(Function<A, T2<B, C>> fn, __<__<µ, S>, B> fb, __<__<µ, S>, C> fc) {
        return Const(getS().apply(asConst(fb).get(), asConst(fc).get()));
    }

    @Override
    default <A> Const<S, A> conquer() {
        return Const(getS().identity());
    }

}
