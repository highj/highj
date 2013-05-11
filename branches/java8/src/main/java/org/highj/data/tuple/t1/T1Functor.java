package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.monad.Functor;

import java.util.function.Function;

public interface T1Functor extends Functor<T1.µ> {
    @Override
    public default <A, B> _<T1.µ, B> map(Function<A, B> fn, _<T1.µ, A> nestedA) {
        return Tuple.narrow(nestedA).map(fn);
    }
}
