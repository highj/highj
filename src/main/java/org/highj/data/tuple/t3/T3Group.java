package org.highj.data.tuple.t3;

import org.derive4j.hkt.__3;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Group;

import static org.highj.Hkt.asT3;

public interface T3Group<A, B, C> extends T3Monoid<A, B, C>, Group<__3<T3.µ, A, B, C>> {
    @Override
    Group<A> getA();

    @Override
    Group<B> getB();

    @Override
    Group<C> getC();

    @Override
    default T3<A, B, C> inverse(__3<T3.µ, A, B, C> value) {
        T3<A, B, C> t3 = asT3(value);
        return T3.of(
            getA().inverse(t3._1()),
            getB().inverse(t3._2()),
            getC().inverse(t3._3()));
    }
}
