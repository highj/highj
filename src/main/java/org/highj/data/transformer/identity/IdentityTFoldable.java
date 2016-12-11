package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTFoldable<M> extends Foldable<__<IdentityT.µ, M>> {

    public Foldable<M> getM();

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        IdentityT<M, A> aId = asIdentityT(nestedA);
        return getM().foldMap(mb, fn, aId.get());
    }
}
