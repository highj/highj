package org.highj.data.tuple.t3;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.comonad.Comonad;

public class T3Comonad<S,T> implements T3Functor<S,T>, Comonad<__.µ<___.µ<T3.µ, S>, T>> {

    @Override
    public <A> _<__.µ<___.µ<T3.µ, S>, T>, _<__.µ<___.µ<T3.µ, S>, T>, A>> duplicate(_<__.µ<___.µ<T3.µ, S>, T>, A> nestedA) {
        T3<S, T, A> triple = Tuple.narrow3(nestedA);
        return Tuple.of(triple._1(), triple._2(), nestedA);
    }

    @Override
    public <A> A extract(_<__.µ<___.µ<T3.µ, S>, T>, A> nestedA) {
        T3<S, T, A> triple = Tuple.narrow3(nestedA);
        return triple._3();
    }
}
