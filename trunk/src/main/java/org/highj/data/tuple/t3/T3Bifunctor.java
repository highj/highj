package org.highj.data.tuple.t3;

import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

public interface T3Bifunctor<S> extends Bifunctor<___.µ<T3.µ, S>>  {

    @Override
    public default <A, B, C, D> T3<S, B, D> bimap(Function<A, B> fn1, Function<C, D> fn2, __<___.µ<T3.µ, S>, A, C> nestedAC) {
        T3<S,A,C> triple = T3.narrow(nestedAC);
        return T3.of(triple._1(), fn1.apply(triple._2()), fn2.apply(triple._3()));
    }
}
