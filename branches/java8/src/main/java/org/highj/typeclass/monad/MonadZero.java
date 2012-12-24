package org.highj.typeclass.monad;


import org.highj._;
import org.highj.data.tuple.T0;
import org.highj.function.F1;

public interface MonadZero<mu> extends Monad<mu> {
    //MonadPlus.mzero (Control.Monad)
    public <A> _<mu, A> mzero();

    //guard (Control.Monad)
    //for MonadZero
    public default _<mu, T0> guard(boolean condition) {
        if (condition) {
            return pure(T0.unit);
        } else {
            return mzero();
        }
    }

    //mfilter (Control.Monad)
    //for MonadZero
    public default <A> _<mu, A> mfilter(F1<A, Boolean> condition, final _<mu, A> target) {
        _<mu, Boolean> result = map(condition, target);
        return bind(result, new F1<Boolean, _<mu, A>>(){
            @Override
            public _<mu, A> $(Boolean b) {
                if (b) {
                    return target;
                } else {
                    return mzero();
                }
            }
        });
    }

}
