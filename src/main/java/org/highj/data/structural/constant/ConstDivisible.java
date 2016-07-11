package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.contravariant.Divisible;

import java.util.function.Function;

import static org.highj.data.structural.Const.narrow;
import static org.highj.data.structural.Const.µ;

public interface ConstDivisible<M> extends ConstContravariant<M>, Divisible<__<µ, M>> {

    Monoid<M> getM();

    @Override
    default <A, B, C> Const<M, A> divide(Function<A, T2<B, C>> fn, __<__<µ, M>, B> fb, __<__<µ, M>, C> fc) {
        return new Const<>(getM().apply(narrow(fb).get(), narrow(fc).get()));
    }

    @Override
    default <A> Const<M, A> conquer() {
        return new Const<>(getM().identity());
    }

}
