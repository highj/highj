package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Applicative;

public interface IdentityTApplicative<M> extends IdentityTApply<M>, Applicative<__<IdentityT.µ, M>> {

    @Override
    public Applicative<M> get();

    @Override
    public default <A> IdentityT<M, A> pure(A a) {
        return new IdentityT<>(get().pure(a));
    }

}
