package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.MonadZero;

import java.util.function.Function;

public interface IdentityTMonadZero<M> extends IdentityTMonadTrans<M>, MonadZero<__<IdentityT.µ, M>> {

    @Override
    public MonadZero<M> get();

    @Override
    public default <A> IdentityT<M, A> mzero() {
        return new IdentityT<M, A>(get().<A>mzero());
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(__<__<IdentityT.µ, M>, Function<A, B>> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        return IdentityTMonadTrans.super.ap(fn, nestedA);
    }

}
