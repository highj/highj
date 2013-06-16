package org.highj.data.tuple.t4;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.tuple.T4;
import org.highj.typeclass1.comonad.Comonad;

public interface T4Comonad<S, T, U> extends T4Functor<S, T, U>, Comonad<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>> {
    @Override
    public default <A> T4<S, T, U, _<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A>> duplicate(_<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, A> quadruple = T4.narrow(nestedA);
        return T4.of(quadruple._1(), quadruple._2(), quadruple._3(), nestedA);
    }

    @Override
    public default <A> A extract(_<__.µ<___.µ<____.µ<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, A> quadruple = T4.narrow(nestedA);
        return quadruple._4();
    }
}
