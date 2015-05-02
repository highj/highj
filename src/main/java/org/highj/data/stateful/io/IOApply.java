package org.highj.data.stateful.io;

import org.highj._;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IOApply extends IOFunctor, Apply<IO.µ> {
    @Override
    default <A, B> IO<B> ap(_<IO.µ, Function<A, B>> fn, _<IO.µ, A> nestedA) {
        return () -> {
            Function<A,B> fn2 = IO.narrow(fn).run(); // <-- this side effect 1st.
            return fn2.apply(IO.narrow(nestedA).run()); // <-- this side effect 2nd.
        };
    }
}
