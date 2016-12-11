package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadPlus;

import static org.highj.Hkt.asListT;

public interface ListTMonadPlus<M> extends ListTMonadZero<M>, MonadPlus<__<ListT.µ,M>> {
    @Override
    Monad<M> get();

    //TODO check why we need this, e.g. if the monad hierarchy is correct regarding Plus
    @Override
    default <A> ListT<M, A> mzero() {
        return ListTMonadZero.super.mzero();
    }

    @Override
    default <A> ListT<M, A> mplus(__<__<ListT.µ, M>, A> one, __<__<ListT.µ, M>, A> two) {
        return ListT.concat(get(), asListT(one), asListT(two));
    }
}
