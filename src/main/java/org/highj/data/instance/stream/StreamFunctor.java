package org.highj.data.instance.stream;

import org.derive4j.hkt.__;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asStream;
import static org.highj.data.Stream.µ;

public interface StreamFunctor extends Functor<µ> {
    @Override
    default <A, B> __<µ, B> map(Function<A, B> fn, __<µ, A> nestedA) {
        return asStream(nestedA).map(fn);
    }
}
