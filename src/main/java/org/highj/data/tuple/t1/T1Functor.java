package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asT1;

public interface T1Functor extends Functor<T1.µ> {
    @Override
    default <A, B> T1<B> map(Function<A, B> fn, __<T1.µ, A> nestedA) {
        return asT1(nestedA).map(fn);
    }
}
