package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.comonad.Comonad;

import java.util.function.Function;

public interface T1Comonad extends Comonad<T1.µ>, T1Functor {
    @Override
    default <A> T1<_<T1.µ, A>> duplicate(_<T1.µ, A> nestedA) {
        return T1.of(nestedA);
    }

    @Override
    default <A> A extract(_<T1.µ, A> nestedA) {
        return T1.narrow(nestedA)._1();
    }

}
