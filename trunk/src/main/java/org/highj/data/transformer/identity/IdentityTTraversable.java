package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

public interface IdentityTTraversable<M> extends Traversable<_<IdentityT.µ,M>>{

    public Traversable<M> getM();

    @Override
    public default <A, B> IdentityT<M, B> map(Function<A, B> fn, _<_<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return new IdentityT<>(getM().map(fn, aId.get()));
    }

    @Override
    public default <A, X> _<X, _<_<IdentityT.µ, M>, A>> sequenceA(Applicative<X> applicative, _<_<IdentityT.µ, M>, _<X, A>> traversable) {
        IdentityT<M, _<X, A>> traversableId = IdentityT.narrow(traversable);
        _<X, _<M, A>> result = getM().sequenceA(applicative, traversableId.get());
        return applicative.map(v -> new IdentityT(v), result);
    }

}
