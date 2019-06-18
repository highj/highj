package org.highj.data.tuple.t4;

import org.derive4j.hkt.__4;
import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Monoid;

public interface T4Monoid<A, B, C, D> extends T4Semigroup<A, B, C, D>, Monoid<__4<T4.µ, A, B, C, D>> {
    @Override
    Monoid<A> getA();

    @Override
    Monoid<B> getB();

    @Override
    Monoid<C> getC();

    @Override
    Monoid<D> getD();

    @Override
    default T4<A, B, C, D> identity() {
        return T4.of(
            getA().identity(),
            getB().identity(),
            getC().identity(),
            getD().identity());
    }
}
