package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.comonad.Comonad;

import static org.highj.Hkt.asT1;

public interface T1Comonad extends Comonad<T1.µ>, T1Functor {
    @Override
    default <A> T1<__<T1.µ, A>> duplicate(__<T1.µ, A> nestedA) {
        return T1.of(nestedA);
    }

    @Override
    default <A> A extract(__<T1.µ, A> nestedA) {
        return asT1(nestedA)._1();
    }

}
