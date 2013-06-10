package org.highj.data.tuple.t2;

import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public interface T2Monoid<A, B> extends T2Semigroup<A, B>, Monoid<T2<A, B>> {

    @Override
    public Monoid<A> getA();

    @Override
    public Monoid<B> getB();

    @Override
    public default T2<A, B> identity() {
        return Tuple.of(getA().identity(), getB().identity());
    }

    public static <A, B> Monoid<T2<A, B>> from(Monoid<A> monoidA, Monoid<B> monoidB) {
        return new T2Monoid<A, B>() {
            @Override
            public Monoid<A> getA() {
                return monoidA;
            }

            @Override
            public Monoid<B> getB() {
                return monoidB;
            }
        };
    }
}
