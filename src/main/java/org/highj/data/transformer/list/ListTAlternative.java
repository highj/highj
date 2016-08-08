package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.alternative.Alternative;
import org.highj.typeclass1.monad.Monad;

public interface ListTAlternative<M> extends ListTApplicative<M>, ListTPlus<M>, Alternative<__<ListT.µ, M>>{

    @Override
    Monad<M> get();
}
