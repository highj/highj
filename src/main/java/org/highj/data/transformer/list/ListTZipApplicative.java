package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asListT;

public interface ListTZipApplicative<M> extends ListTFunctor<M>, Applicative<__<ListT.µ, M>> {
    @Override
    Monad<M> get();

    @Override
    default <A> ListT<M, A> pure(A a) {
        return ListT.repeat(get(), a);
    }

    @Override
    default <A, B> ListT<M, B> ap(__<__<ListT.µ, M>, Function<A, B>> fn, __<__<ListT.µ, M>, A> nestedA) {
        return ListT.zipWith(get(), Function::apply, asListT(fn), asListT(nestedA));
    }
}
