package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.MonadZero;

public interface IdentityTMonadZero<M> extends IdentityTMonadTrans<M>, MonadZero<_<IdentityT.µ,M>> {

    @Override
    public MonadZero<M> get();

    @Override
    public default <A> _<_<IdentityT.µ, M>, A> mzero() {
        return new IdentityT<M,A>(get().<A>mzero());
    }
}
