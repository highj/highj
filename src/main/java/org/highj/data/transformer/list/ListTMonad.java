package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;

public interface ListTMonad<M> extends ListTApplicative<M>, ListTBind<M>, Monad<__<ListT.µ, M>> {
    @Override
    Monad<M> get();
}
