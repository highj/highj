package org.highj.data.structural.constant;

import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Biapplicative;

public interface ConstBiapplicative extends ConstBiapply, Biapplicative<Const.µ> {
    @Override
    public default <A, B> Const<A, B> bipure(A a, B b) {
        return new Const<>(a);
    }
}
