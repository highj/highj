package org.highj.data.tuple.t2;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.comonad.Comonad;

import static org.highj.Hkt.asT2;

public interface T2Comonad<S> extends Comonad<__<T2.µ, S>>, T2Functor<S> {
    @Override
    default <A> T2<S, __<__<T2.µ, S>, A>> duplicate(__<__<T2.µ, S>, A> nestedA) {
        T2<S, A> pair = asT2(nestedA);
        return T2.of(pair._1(), nestedA);
    }

    @Override
    default <A> A extract(__<__<T2.µ, S>, A> nestedA) {
        return asT2(nestedA)._2();
    }
}
