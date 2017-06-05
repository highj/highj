package org.highj.data.structural.constant;

import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Semigroup;

import static org.highj.data.structural.Const.Const;

public interface ConstSemigroup<S, A> extends Semigroup<Const<S, A>> {

    Semigroup<S> getS();

    @Override
    default Const<S, A> apply(Const<S, A> x, Const<S, A> y) {
        return Const(getS().apply(x.get(), y.get()));
    }
}
