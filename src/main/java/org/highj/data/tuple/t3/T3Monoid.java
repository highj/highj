package org.highj.data.tuple.t3;

import org.derive4j.hkt.__3;
import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;

public interface T3Monoid<A, B, C> extends T3Semigroup<A, B, C>, Monoid<__3<T3.µ, A, B, C>> {
    @Override
    Monoid<A> getA();

    @Override
    Monoid<B> getB();

    @Override
    Monoid<C> getC();

    @Override
    default T3<A, B, C> identity() {
        return T3.of(
            getA().identity(),
            getB().identity(),
            getC().identity());
    }
}
