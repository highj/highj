package org.highj.data.tuple.t4;

import org.derive4j.hkt.__4;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Group;

import static org.highj.Hkt.asT4;

public interface T4Group<A, B, C, D> extends T4Monoid<A, B, C, D>, Group<__4<T4.µ, A, B, C, D>> {
    @Override
    Group<A> getA();

    @Override
    Group<B> getB();

    @Override
    Group<C> getC();

    @Override
    Group<D> getD();

    @Override
    default T4<A, B, C, D> inverse(__4<T4.µ, A, B, C, D> value) {
        T4<A, B, C, D> t4 = asT4(value);
        return T4.of(
            getA().inverse(t4._1()),
            getB().inverse(t4._2()),
            getC().inverse(t4._3()),
            getD().inverse(t4._4()));
    }
}
