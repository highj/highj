package org.highj.typeclass.monad;


import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.function.F1;

/*
 * Note that there is no MonadZeroAbstract class.
 *
 * Instead, inherit from MonadAbstract, implement MonadZero and overwrite the mzero method.
 */
public interface MonadZero<µ> extends Monad<µ> {
    //MonadPlus.mzero (Control.Monad)
    public <A> _<µ, A> mzero();

    //guard (Control.Monad)
    public _<µ, T0> guard(boolean condition);

    //mfilter (Control.Monad)
    public <A> _<µ, A> mfilter(F1<A, Boolean> condition, _<µ, A> target);
}
