package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable1;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IdentityTTraversable1<M> extends
        IdentityTTraversable<M>, IdentityTFoldable<M>, Traversable1<_<IdentityT.µ,M>> {
    @Override
    public Traversable1<M> getM();

    @Override
    public default <A,B,F> _<F,_<_<IdentityT.µ,M>,B>> traverse1(Apply<F> apply, Function<A,_<F,B>> fn, _<_<IdentityT.µ,M>,A> traversable) {
        IdentityT<M,A> traversableM = IdentityT.narrow(traversable);
        return apply.map(IdentityT::new, getM().traverse1(apply, fn, traversableM.get()));
    }

    @Override
    public default <B,F> _<F,_<_<IdentityT.µ,M>,B>> sequence1(Apply<F> apply, _<_<IdentityT.µ,M>,_<F,B>> traversable) {
        IdentityT<M,_<F,B>> traversableF = IdentityT.narrow(traversable);
        return apply.map(IdentityT::new, getM().sequence1(apply, traversableF.get()));
    }

    @Override
    public default <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, _<_<IdentityT.µ, M>, A> nestedA) {
        return IdentityTFoldable.super.foldMap(mb,fn,nestedA);
    }
}
