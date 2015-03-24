package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface IdentityTFunctor<M> extends Functor<__.µ<IdentityT.µ, M>> {

    Functor<M> get();

    @Override
    public default <A, B> __<IdentityT.µ, M, B> map(Function<A, B> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return new IdentityT<>(get().map(fn, aId.get()));
    }
}
