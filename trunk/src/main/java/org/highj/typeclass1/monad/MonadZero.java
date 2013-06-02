package org.highj.typeclass1.monad;


import org.highj._;
import org.highj.data.tuple.T0;

import java.util.function.Function;

public interface MonadZero<µ> extends Monad<µ> {
    //MonadPlus.mzero (Control.Monad)
    public <A> _<µ, A> mzero();

    //guard (Control.Monad)
    //for MonadZero
    public default _<µ, T0> guard(boolean condition) {
        return condition ? pure(T0.unit) : MonadZero.this.<T0>mzero();
    }

    //mfilter (Control.Monad)
    //for MonadZero
    public default <A> _<µ, A> mfilter(Function<A, Boolean> condition, final _<µ, A> target) {
        return bind(map(condition, target), b -> b ? target : MonadZero.this.<A>mzero());
    }

}
