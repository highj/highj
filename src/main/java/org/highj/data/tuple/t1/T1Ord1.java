package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ord1;
import org.highj.data.tuple.T1;

public interface T1Ord1 extends Ord1<T1.µ> {
    @Override
    default <A> Ord<__<T1.µ, A>> cmp(Ord<? super A> ord) {
        return (one, two) -> ord.cmp(Hkt.asT1(one).get(), Hkt.asT1(two).get());
    }
}
