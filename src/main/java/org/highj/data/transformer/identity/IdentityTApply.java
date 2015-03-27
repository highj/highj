package org.highj.data.transformer.identity;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Apply;

public interface IdentityTApply<M> extends IdentityTFunctor<M>, Apply<__.µ<IdentityT.µ, M>> {

    @Override
    public Apply<M> get();

    @Override
    public default <A, B> IdentityT<M, B> ap(_<__.µ<IdentityT.µ, M>, Function<A, B>> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        IdentityT<M, Function<A, B>> fnId = IdentityT.narrow(fn);
        return new IdentityT<>(get().ap(fnId.get(), aId.get()));
    }
}
