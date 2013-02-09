package org.highj.data.transformer.identity;

import org.highj._;
import org.highj.data.transformer.IdentityT;
import org.highj.function.Functions;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface IdentityTBind<M> extends IdentityTApply<M>, Bind<_<IdentityT.µ, M>> {

    @Override
    public Bind<M> get();

    @Override
    public default <A, B> _<_<IdentityT.µ, M>, B> bind(_<_<IdentityT.µ, M>, A> nestedA, Function<A, _<_<IdentityT.µ, M>, B>> fn) {
        IdentityT<M, A> aId = IdentityT.narrow(nestedA);
        return new IdentityT<>(get().bind(aId.get(), Functions.compose(a -> IdentityT.<M, B>narrow(a).get(), fn)));
    }
}
