package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public interface ListTApply<M> extends ListTFunctor<M>, Apply<__<ListT.µ, M>> {

    Monad<M> get();

    default <A, B> ListT<M, B> ap(__<__<ListT.µ, M>, Function<A, B>> fn, __<__<ListT.µ, M>, A> nestedA) {
        ListT<M,__<M,Function<A,B>>> mfn = map(fun -> get().pure(fun), ListT.narrow(fn));
        ListT<M, __<M, A>> ma = map(a -> get().pure(a), ListT.narrow(nestedA));
        return null; //lift2(f ->  x -> get().ap(f, x)).apply(mfn).apply(ma);
    }
}
