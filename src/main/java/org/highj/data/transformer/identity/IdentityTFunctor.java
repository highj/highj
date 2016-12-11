package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTFunctor<M> extends Functor<__<IdentityT.µ, M>> {

    Functor<M> get();

    @Override
    public default <A, B> IdentityT<M, B> map(Function<A, B> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = asIdentityT(nestedA);
        return new IdentityT<>(get().map(fn, aId.get()));
    }
}
