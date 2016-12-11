package org.highj.data.transformer.list;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.functor.Functor;

import static org.highj.Hkt.asListT;

public interface ListTFunctor<M> extends Functor<__<ListT.µ, M>> {
    Functor<M> get();

    @Override
    default <A, B> ListT<M, B> map(Function<A, B> fn, __<__<ListT.µ, M>, A> nestedA) {
        Function<ListT<M, A>, ListT<M, B>> g = la -> map(fn, la);
        return asListT(nestedA).stepMap(get(),
                stepYield -> ListT.yield(fn.apply(stepYield.head()), stepYield.mapTail(g)),
                stepSkip -> ListT.skip(stepSkip.mapTail(g)),
                () -> ListT.done());
    }
}
