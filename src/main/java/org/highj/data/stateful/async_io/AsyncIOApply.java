package org.highj.data.stateful.async_io;

import org.derive4j.hkt.__;
import org.highj.data.stateful.AsyncIO;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface AsyncIOApply extends AsyncIOFunctor, Apply<AsyncIO.µ> {
    @Override
    default <A, B> __<AsyncIO.µ, B> ap(__<AsyncIO.µ, Function<A, B>> mf, __<AsyncIO.µ, A> ma) {
        return AsyncIO.bind.bind(
            mf,
            (Function<A,B> f) ->
                AsyncIO.functor.map(
                    f,
                    ma
                )
        );
    }
}
