package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

import static org.highj.data.structural.Const.Const;
import static org.highj.data.structural.Const.µ;

public interface ConstApplicative<S> extends Applicative<__<µ, S>>, ConstApply<S> {

    Monoid<S> getS();

    @Override
    default <A> Const<S, A> pure(A a) {
        return Const(getS().identity());
    }
}
