package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.alternative.Plus;
import org.highj.typeclass1.monad.Applicative;

public interface ListTPlus<M> extends ListTAlt<M>, Plus<__<ListT.µ, M>> {

    @Override
    Applicative<M> get();

    @Override
    default <A> ListT<M, A> mzero() {
        return ListT.nil(get());
    }
}
