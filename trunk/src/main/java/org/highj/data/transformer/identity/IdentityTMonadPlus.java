package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

public interface IdentityTMonadPlus<M> extends IdentityTMonadZero<M>, MonadPlus<_<IdentityT.µ,M>> {

    public MonadPlus<M> get();

    @Override
    public default <A> IdentityT<M, A> mplus(_<_<IdentityT.µ, M>, A> one, _<_<IdentityT.µ, M>, A> two) {
        IdentityT<M, A> oneId = IdentityT.narrow(one);
        IdentityT<M, A> twoId = IdentityT.narrow(two);
        return new IdentityT<M,A>(get().mplus(oneId.get(), twoId.get()));
    }

    //needed to resolve conflict between IdentityTMonadZero.mzero and Plus.mzero
    @Override
    public default <A> IdentityT<M, A> mzero() {
        return new IdentityT<>(get().<A>mzero());
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(_<_<IdentityT.µ, M>, Function<A, B>> fn, _<_<IdentityT.µ, M>, A> nestedA) {
       return  IdentityTMonadZero.super.ap(fn, nestedA);
    }
}