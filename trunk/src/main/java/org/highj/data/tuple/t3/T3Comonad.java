package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.comonad.Comonad;

public interface T3Comonad<S,T> extends T3Functor<S,T>, Comonad<__.µ<___.µ<T3.µ, S>, T>> {

    @Override
    public default <A> T3<S, T, _<__.µ<___.µ<T3.µ, S>, T>, A>> duplicate(_<__.µ<___.µ<T3.µ, S>, T>, A> nestedA) {
        T3<S, T, A> triple = T3.narrow(nestedA);
        return T3.of(triple._1(), triple._2(), nestedA);
    }

    @Override
    public default <A> A extract(_<__.µ<___.µ<T3.µ, S>, T>, A> nestedA) {
        T3<S, T, A> triple = T3.narrow(nestedA);
        return triple._3();
    }
}
