package org.highj.data.tuple.t2;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.comonad.Comonad;

public interface T2Comonad<S> extends Comonad<__.µ<T2.µ, S>>, T2Functor<S> {
    @Override
    public default <A> T2<S, _<__.µ<T2.µ, S>, A>> duplicate(_<__.µ<T2.µ, S>, A> nestedA) {
        T2<S, A> pair = T2.narrow(nestedA);
        return T2.of(pair._1(), nestedA);
    }

    @Override
    public default <A> A extract(_<__.µ<T2.µ, S>, A> nestedA) {
        T2<S, A> pair = T2.narrow(nestedA);
        return pair._2();
    }
}
