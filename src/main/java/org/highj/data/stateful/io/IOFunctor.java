package org.highj.data.stateful.io;

import org.highj._;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface IOFunctor extends Functor<IO.µ> {
    @Override
    default <A, B> IO<B> map(Function<A, B> fn, _<IO.µ, A> nestedA) {
        return IO.narrow(nestedA).map(fn);
    }
}
