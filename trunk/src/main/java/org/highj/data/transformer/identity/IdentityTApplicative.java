package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Applicative;

public interface IdentityTApplicative<M> extends IdentityTApply<M>, Applicative<_<IdentityT.µ, M>> {

    @Override
    public Applicative<M> get();

    @Override
    public default <A> IdentityT<M, A> pure(A a) {
        return new IdentityT<>(get().pure(a));
    }

}
