package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;

public interface IdentityTFoldable<M> extends Foldable<_<IdentityT.µ,M>> {

    public Foldable<M> getM();

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, _<_<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return getM().foldMap(mb, fn, aId.get());
    }
}
