package org.highj.data.stateful.io;

import org.derive4j.hkt.__;
import org.highj.data.stateful.IO;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface IOFunctor extends Functor<IO.µ> {
    @Override
    default <A, B> IO<B> map(Function<A, B> fn, __<IO.µ, A> nestedA) {
        return () -> fn.apply(IO.narrow(nestedA).run());
    }
}
