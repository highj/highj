package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Functor;

import java.util.function.Function;

public interface IdentityTFunctor<M> extends Functor<_<IdentityT.µ, M>> {

    Functor<M> get();

    @Override
    public default <A, B> _<_<IdentityT.µ, M>, B> map(Function<A, B> fn, _<_<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return new IdentityT<>(get().map(fn, aId.get()));
    }
}
