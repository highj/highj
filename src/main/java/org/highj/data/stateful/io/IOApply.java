package org.highj.data.stateful.io;

import org.highj._;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

public interface IOApply extends IOFunctor, Apply<IO.µ> {
    @Override
    default <A, B> IO<B> ap(_<IO.µ, Function<A, B>> fn, _<IO.µ, A> nestedA) {
        return IO.narrow(nestedA).ap(IO.narrow(fn));
    }

}
