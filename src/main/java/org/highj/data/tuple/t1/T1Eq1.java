package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.tuple.T1;

public interface T1Eq1 extends Eq1<T1.µ> {

    @Override
    default <T> Eq<__<T1.µ, T>> eq1(Eq<? super T> eq) {
        return (one, two) -> eq.eq(Hkt.asT1(one).get(), Hkt.asT1(two).get());
    }
}
