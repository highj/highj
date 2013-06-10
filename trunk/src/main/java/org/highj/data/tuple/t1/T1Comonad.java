package org.highj.data.tuple.t1;

import org.highj._;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass1.comonad.Comonad;

public class T1Comonad implements Comonad<T1.µ>, T1Functor {
    @Override
    public <A> T1<_<T1.µ, A>> duplicate(_<T1.µ, A> nestedA) {
        return Tuple.of(nestedA);
    }

    @Override
    public <A> A extract(_<T1.µ, A> nestedA) {
        return Tuple.narrow(nestedA)._1();
    }

}
