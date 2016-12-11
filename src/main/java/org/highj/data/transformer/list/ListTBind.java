package org.highj.data.transformer.list;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import static org.highj.Hkt.asListT;

public interface ListTBind<M> extends ListTApply<M>, Bind<__<ListT.µ, M>> {

    @Override
    Monad<M> get();

    @Override
    default <A, B> ListT<M, B> bind(__<__<ListT.µ, M>, A> nestedA, Function<A, __<__<ListT.µ, M>, B>> fn) {
        return asListT(nestedA).stepMap(get(),
                stepYield -> ListT.skip(stepYield.mapTail(s ->
                        ListT.concat(get(),
                                asListT(fn.apply(stepYield.head())),
                                bind(s, fn)))),
                stepSkip -> ListT.skip(stepSkip.mapTail(s -> bind(s, fn))),
                () -> ListT.done());
    }
}
