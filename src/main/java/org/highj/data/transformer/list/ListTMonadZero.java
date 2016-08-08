package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadZero;

public interface ListTMonadZero<M> extends ListTMonad<M>, MonadZero<__<ListT.µ,M>> {

    @Override
    Monad<M> get();

    @Override
    default <A> ListT<M, A> mzero() {
        return ListT.nil(get());
    }
}
