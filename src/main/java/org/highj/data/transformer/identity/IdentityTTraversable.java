package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

public interface IdentityTTraversable<M> extends Traversable<__.µ<IdentityT.µ, M>> {

    public Traversable<M> getM();

    @Override
    public default <A, B> __<IdentityT.µ, M, B> map(Function<A, B> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return new IdentityT<>(getM().map(fn, aId.get()));
    }

    @Override
    public default <A, X> _<X, _<__.µ<IdentityT.µ, M>, A>> sequenceA(Applicative<X> applicative, _<__.µ<IdentityT.µ, M>, _<X, A>> traversable) {
        IdentityT<M, _<X, A>> traversableId = IdentityT.narrow(traversable);
        _<X, _<M, A>> result = getM().sequenceA(applicative, traversableId.get());
        return applicative.map(v -> new IdentityT(v), result);
    }

}
