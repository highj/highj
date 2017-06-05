package org.highj.data.structural.constant;

import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Monoid;

import static org.highj.data.structural.Const.Const;

public interface ConstMonoid<S, A> extends Monoid<Const<S, A>>, ConstSemigroup<S, A> {

    @Override
    Monoid<S> getS();

    @Override
    default Const<S, A> identity() {
        return Const(getS().identity());
    }
}
