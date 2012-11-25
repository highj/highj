package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass.alternative.Alternative;

/*
 * Note that there is no MonadPlusAbstract class.
 *
 * Instead, inherit from MonadAbstract, implement MonadPlus and overwrite the mzero and mplus method.
 */

public interface MonadPlus<µ> extends MonadZero<µ>, Alternative<µ> {

    //MonadPlus.(++) (Control.Monad)
    public <A> _<µ, A> mplus(_<µ, A> one, _<µ, A> two);

    //mplus (Control.Monad)
    public <A> _<µ, A> msum(_<List.µ, _<µ, A>> list);
}
