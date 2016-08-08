package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.alternative.Alt;
import org.highj.typeclass1.monad.Applicative;

public interface ListTAlt<M> extends ListTFunctor<M>, Alt<__<ListT.µ,M>> {
    Applicative<M> get();

    @Override
    default <A> ListT<M, A> mplus(__<__<ListT.µ, M>, A> first, __<__<ListT.µ, M>, A> second) {
        return ListT.concat(get(), ListT.narrow(first), ListT.narrow(second));
    }
}
