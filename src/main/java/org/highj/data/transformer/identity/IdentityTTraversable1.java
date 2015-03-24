package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IdentityTTraversable1<M> extends
        IdentityTTraversable<M>, IdentityTFoldable<M>, Traversable1<__.µ<IdentityT.µ, M>> {
    @Override
    public Traversable1<M> getM();

    @Override
    public default <A, B, F> _<F, _<__.µ<IdentityT.µ, M>, B>> traverse1(Apply<F> apply, Function<A, _<F, B>> fn, _<__.µ<IdentityT.µ, M>, A> traversable) {
        IdentityT<M,A> traversableM = IdentityT.narrow(traversable);
        _<F, _<M, B>> fmb = getM().traverse1(apply, fn, traversableM.get());
        return apply.<_<M, B>, _<__.µ<IdentityT.µ, M>, B>>map(IdentityT::new, fmb);
    }

    @Override
    public default <B, F> _<F, _<__.µ<IdentityT.µ, M>, B>> sequence1(Apply<F> apply, _<__.µ<IdentityT.µ, M>, _<F, B>> traversable) {
        IdentityT<M,_<F,B>> traversableF = IdentityT.narrow(traversable);
        return apply.<_<M, B>, _<__.µ<IdentityT.µ, M>, B>>map(IdentityT::new, getM().sequence1(apply, traversableF.get()));
    }

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        return IdentityTFoldable.super.foldMap(mb,fn,nestedA);
    }
}
