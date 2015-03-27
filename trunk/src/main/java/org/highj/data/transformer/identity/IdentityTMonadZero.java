package org.highj.data.transformer.identity;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.MonadZero;

public interface IdentityTMonadZero<M> extends IdentityTMonadTrans<M>, MonadZero<__.µ<IdentityT.µ, M>> {

    @Override
    public MonadZero<M> get();

    @Override
    public default <A> IdentityT<M, A> mzero() {
        return new IdentityT<M, A>(get().<A>mzero());
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(_<__.µ<IdentityT.µ, M>, Function<A, B>> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        return IdentityTMonadTrans.super.ap(fn, nestedA);
    }

}
