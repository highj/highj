package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

public interface ListTApplicative<M> extends ListTApply<M>, Applicative<__<ListT.µ,M>> {
    @Override
    Monad<M> get();

    @Override
    default <A> ListT<M, A> pure(A a) {
        return ListT.singleton(get(), a);
    }
}
