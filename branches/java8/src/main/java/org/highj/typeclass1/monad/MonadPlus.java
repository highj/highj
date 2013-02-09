package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.alternative.Alternative;

public interface MonadPlus<µ> extends MonadZero<µ>, Alternative<µ> {

    //MonadPlus.(++) (Control.Monad)
    public <A> _<µ, A> mplus(_<µ, A> one, _<µ, A> two);


    //msum (Control.Monad)
    public default <A> _<µ, A> msum(_<List.µ, _<µ, A>> list) {
        return List.narrow(list).foldr((_<µ, A> one) -> (_<µ, A> two) -> mplus(one, two), this.<A>mzero());
    }
}
