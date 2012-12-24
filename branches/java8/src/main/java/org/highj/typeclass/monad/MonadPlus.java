package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.function.F2;
import org.highj.typeclass.alternative.Alternative;
import org.highj.typeclass.group.Monoid;

//minimal complete definition: mlus OR mplus(one, two)
public interface MonadPlus<mu> extends MonadZero<mu>, Alternative<mu> {

    //MonadPlus.(++) (Control.Monad)
    public default <A> _<mu, A> mplus(_<mu, A> one, _<mu, A> two){
        return this.<A>mplus().$(one, two);
    }

    //MonadPlus.(++) (Control.Monad)
    public default <A> F2<_<mu, A>,_<mu, A>,_<mu, A>> mplus() {
        return new F2<_<mu, A>, _<mu, A>, _<mu, A>>() {
            @Override
            public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
                return mplus(one, two);
            }
        };
    }

    //msum (Control.Monad)
    public default <A> _<mu, A> msum(_<List.µ, _<mu, A>> list) {
        List<_<mu, A>> as = List.narrow(list);
        _<mu, A> zero = mzero();
        return as.foldr(new F2<_<mu, A>,_<mu, A>,_<mu, A>>(){
            @Override
            public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
                return mplus(one, two);
            }
        }, zero);
    }
}
