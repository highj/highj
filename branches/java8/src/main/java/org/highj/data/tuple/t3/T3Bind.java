package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.monad.Bind;

import java.util.function.Function;

public interface T3Bind<S,T> extends T3Apply<S,T>, Bind<__.µ<___.µ<T3.µ, S>, T>> {

    @Override
    public default <A, B> _<__.µ<___.µ<T3.µ, S>, T>, B> bind(_<__.µ<___.µ<T3.µ, S>, T>, A> nestedA,
                                                             Function<A, _<__.µ<___.µ<T3.µ, S>, T>, B>> fn) {
        T3<S, T, A> ta = Tuple.narrow3(nestedA);
        T3<S, T, B> tb = Tuple.narrow3(fn.apply(ta._3()));
        return Tuple.of(getS().dot(ta._1(), tb._1()), getT().dot(ta._2(), tb._2()), tb._3());
    }

}
