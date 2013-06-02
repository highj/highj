package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IdentityTApply<M> extends IdentityTFunctor<M>, Apply<_<IdentityT.µ, M>> {

    @Override
    public Apply<M> get();

    @Override
    public default <A, B> _<_<IdentityT.µ, M>, B> ap(_<_<IdentityT.µ, M>, Function<A, B>> fn, _<_<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        IdentityT<M, Function<A, B>> fnId = IdentityT.narrow(fn);
        return new IdentityT<>(get().ap(fnId.get(), aId.get()));
    }
}
