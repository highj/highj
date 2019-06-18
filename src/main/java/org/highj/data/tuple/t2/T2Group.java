package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Group;

import static org.highj.Hkt.asT2;

public interface T2Group<A, B> extends T2Monoid<A, B>, Group<__2<T2.µ, A, B>> {
    @Override
    Group<A> getA();

    @Override
    Group<B> getB();

    @Override
    default T2<A, B> inverse(__2<T2.µ, A, B> value) {
        T2<A, B> t2 = asT2(value);
        return T2.of(getA().inverse(t2._1()), getB().inverse(t2._2()));
    }
}
