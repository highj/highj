package org.highj.data.tuple.t4;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T4;
import org.highj.typeclass1.comonad.Comonad;

public interface T4Comonad<S, T, U> extends T4Functor<S, T, U>, Comonad<__<__<__<T4.µ, S>, T>, U>> {
    @Override
    default <A> T4<S, T, U, __<__<__<__<T4.µ, S>, T>, U>, A>> duplicate(__<__<__<__<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, A> quadruple = T4.narrow(nestedA);
        return T4.of(quadruple._1(), quadruple._2(), quadruple._3(), nestedA);
    }

    @Override
    default <A> A extract(__<__<__<__<T4.µ, S>, T>, U>, A> nestedA) {
        T4<S, T, U, A> quadruple = T4.narrow(nestedA);
        return quadruple._4();
    }
}
