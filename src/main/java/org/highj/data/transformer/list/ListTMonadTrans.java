package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;

public interface ListTMonadTrans<M> extends ListTMonad<M>, MonadTrans<ListT.µ, M> {

    @Override
    Monad<M> get();

    @Override
    default <A> ListT<M, A> lift(__<M, A> nestedA) {
        return ListT.fromEffect(get(), nestedA);
    }
}
