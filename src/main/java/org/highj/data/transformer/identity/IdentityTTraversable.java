package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTTraversable<M> extends Traversable<__<IdentityT.µ, M>> {

    Traversable<M> getM();

    @Override
    default <A, B> IdentityT<M, B> map(Function<A, B> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = asIdentityT(nestedA);
        return new IdentityT<>(getM().map(fn, aId.get()));
    }

    @Override
    default <A, X> __<X, __<__<IdentityT.µ, M>, A>> sequenceA(Applicative<X> applicative, __<__<IdentityT.µ, M>, __<X, A>> traversable) {
        IdentityT<M, __<X, A>> traversableId = asIdentityT(traversable);
        __<X, __<M, A>> result = getM().sequenceA(applicative, traversableId.get());
        return applicative.map(IdentityT::new, result);
    }

}
