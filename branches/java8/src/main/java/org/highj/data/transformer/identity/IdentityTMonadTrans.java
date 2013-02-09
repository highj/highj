package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;


public interface IdentityTMonadTrans<M> extends IdentityTBind<M>, IdentityTApplicative<M>, MonadTrans<IdentityT.µ, M> {

    @Override
    public Monad<M> get();

    @Override
    public default <A> _<_<IdentityT.µ, M>, A> lift(_<M, A> nestedA) {
        return new IdentityT<>(nestedA);
    }
}
