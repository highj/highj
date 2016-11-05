package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.stateful.AsyncIO;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface AsyncIOApply<E> extends AsyncIOFunctor<E>, Apply<__<AsyncIO.µ,E>> {
    @Override
    default <A, B> __<__<AsyncIO.µ, E>, B> ap(__<__<AsyncIO.µ, E>, Function<A, B>> mf, __<__<AsyncIO.µ, E>, A> ma) {
        return AsyncIO.<E>bind().bind(
            mf,
            (Function<A,B> f) ->
                AsyncIO.<E>functor().map(
                    f::apply,
                    ma
                )
        );
    }
}
