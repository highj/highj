package org.highj.data.tuple.t2;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;

public interface T2Monoid<A, B> extends T2Semigroup<A, B>, Monoid<__2<T2.µ, A, B>> {

    @Override
    Monoid<A> getA();

    @Override
    Monoid<B> getB();

    @Override
    default T2<A, B> identity() {
        return T2.of(getA().identity(), getB().identity());
    }
}
