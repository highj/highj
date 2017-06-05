package org.highj.data.structural.constant;

import org.highj.data.structural.Const;
import org.highj.typeclass2.bifunctor.Biapplicative;

import static org.highj.data.structural.Const.Const;

public interface ConstBiapplicative extends ConstBiapply, Biapplicative<Const.µ> {
    @Override
    default <S, A> Const<S, A> bipure(S s, A ignored) {
        return Const(s);
    }
}
