package org.highj.data.transformer.list;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Monad;

import static org.highj.Hkt.asListT;

public interface ListTApply<M> extends ListTFunctor<M>, Apply<__<ListT.µ, M>> {

    Monad<M> get();

    //TODO check if this makes any sense
    default <A, B> ListT<M, B> ap(__<__<ListT.µ, M>, Function<A, B>> fn, __<__<ListT.µ, M>, A> nestedA) {
        return ListT.wrapEffect(get(), get().map(mb ->
                mb.map(t2 -> ListT.concat(get(),
                        map(t2._1(), asListT(nestedA)),
                        ap(t2._2(), nestedA)))
                        .getOrElse(() -> ListT.nil(get())),
                asListT(fn).uncons(get())));
    }
}
