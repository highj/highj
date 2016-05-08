package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;

import java.util.function.Function;


public interface IdentityTMonadTrans<M> extends IdentityTBind<M>, IdentityTApplicative<M>, MonadTrans<IdentityT.µ, M> {

    @Override
    public Monad<M> get();

    @Override
    public default <A> IdentityT<M, A> lift(__<M, A> nestedA) {
        return new IdentityT<>(nestedA);
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(__<__<IdentityT.µ, M>, Function<A, B>> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        return IdentityTApplicative.super.ap(fn, nestedA);
    }

}
