package org.highj.data.transformer.identity;

import org.derive4j.hkt.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asIdentityT;

public interface IdentityTTraversable1<M> extends
        IdentityTTraversable<M>, IdentityTFoldable<M>, Traversable1<__<IdentityT.µ, M>> {
    @Override
    public Traversable1<M> getM();

    @Override
    public default <A, B, F> __<F, __<__<IdentityT.µ, M>, B>> traverse1(Apply<F> apply, Function<A, __<F, B>> fn, __<__<IdentityT.µ, M>, A> traversable) {
        IdentityT<M, A> traversableM = asIdentityT(traversable);
        __<F, __<M, B>> fmb = getM().traverse1(apply, fn, traversableM.get());
        return apply.<__<M, B>, __<__<IdentityT.µ, M>, B>>map(IdentityT::new, fmb);
    }

    @Override
    public default <B, F> __<F, __<__<IdentityT.µ, M>, B>> sequence1(Apply<F> apply, __<__<IdentityT.µ, M>, __<F, B>> traversable) {
        IdentityT<M, __<F, B>> traversableF = asIdentityT(traversable);
        return apply.<__<M, B>, __<__<IdentityT.µ, M>, B>>map(IdentityT::new, getM().sequence1(apply, traversableF.get()));
    }

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, __<__<IdentityT.µ, M>, A> nestedA) {
        return IdentityTFoldable.super.foldMap(mb, fn, nestedA);
    }
}
