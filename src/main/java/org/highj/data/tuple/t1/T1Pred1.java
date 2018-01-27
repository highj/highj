package org.highj.data.tuple.t1;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.predicates.Pred;
import org.highj.data.predicates.Pred1;
import org.highj.data.tuple.T1;

public interface T1Pred1 extends Pred1<T1.µ> {

    @Override
    default <A> Pred<__<T1.µ, A>> pred1(Pred<A> pred) {
        return ta -> pred.test(Hkt.asT1(ta)._1());
    }
}
