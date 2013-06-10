package org.highj.data.structural.constant;

import org.highj._;
import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

import static org.highj.data.structural.Const.µ;

public interface ConstApplicative<S> extends Applicative<__.µ<µ,S>>, ConstApply<S> {

    public Monoid<S> getS();

    @Override
    public default <A> Const<S, A> pure(A a) {
        return new Const<>(getS().identity());
    }
}
