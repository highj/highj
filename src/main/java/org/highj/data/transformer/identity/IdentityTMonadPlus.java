package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.MonadPlus;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTMonadPlus<M> extends IdentityTMonadZero<M>, MonadPlus<__<IdentityT.µ, M>> {

    public MonadPlus<M> get();

    @Override
    public default <A> IdentityT<M, A> mplus(__<__<IdentityT.µ, M>, A> one, __<__<IdentityT.µ, M>, A> two) {
        IdentityT<M, A> oneId = asIdentityT(one);
        IdentityT<M, A> twoId = asIdentityT(two);
        return new IdentityT<M, A>(get().mplus(oneId.get(), twoId.get()));
    }

    //needed to resolve conflict between IdentityTMonadZero.mzero and Plus.mzero
    @Override
    public default <A> IdentityT<M, A> mzero() {
        return new IdentityT<>(get().<A>mzero());
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(__<__<IdentityT.µ, M>, Function<A, B>> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        return IdentityTMonadZero.super.ap(fn, nestedA);
    }

}