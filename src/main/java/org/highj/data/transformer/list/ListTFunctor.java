package org.highj.data.transformer.list;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.functor.Functor;

public interface ListTFunctor<M> extends Functor<__<ListT.µ, M>> {
    Functor<M> get();

    @Override
    default <A, B> ListT<M, B> map(Function<A, B> fn, __<__<ListT.µ, M>, A> nestedA) {
        Function<ListT<M, A>, ListT<M, B>> g = l -> map(fn, l);
        return ListT.narrow(nestedA).stepMap(get(), step -> step.map(
                stepYield -> ListT.yield(fn.apply(stepYield.yield()), stepYield.mapGet(g)),
                stepSkip -> ListT.skip(stepSkip.mapGet(g)),
                ListT::done));
    }
}
