package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

public interface T1Functor extends Functor<T1.µ> {
    @Override
    public default <A, B> T1<B> map(Function<A, B> fn, _<T1.µ, A> nestedA) {
        return T1.narrow(nestedA).map(fn);
    }
}
