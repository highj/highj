package org.highj.data.tuple.t3;

import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Monoid;

public interface T3Monoid<A, B, C> extends T3Semigroup<A, B, C>, Monoid<T3<A, B, C>> {

    @Override
    public Monoid<A> getA();

    @Override
    public Monoid<B> getB();

    @Override
    public Monoid<C> getC();

    @Override
    public default  T3<A, B, C> identity() {
        return T3.of(getA().identity(), getB().identity(), getC().identity());
    }

    public static <A, B, C> Monoid<T3<A, B, C>> from(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC) {
        return new T3Monoid<A, B, C>() {
            @Override
            public Monoid<A> getA() {
                return monoidA;
            }

            @Override
            public Monoid<B> getB() {
                return monoidB;
            }

            @Override
            public Monoid<C> getC() {
                return monoidC;
            }
        };
    }
}
