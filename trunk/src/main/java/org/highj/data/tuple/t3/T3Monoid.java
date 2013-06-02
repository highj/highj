package org.highj.data.tuple.t3;

import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public abstract class T3Monoid<A, B, C> extends T3Semigroup<A, B, C> implements Monoid<T3<A, B, C>> {

    @Override
    protected abstract Monoid<A> getA();

    @Override
    protected abstract Monoid<B> getB();

    @Override
    protected abstract Monoid<C> getC();

    @Override
    public T3<A, B, C> identity() {
        return Tuple.of(getA().identity(), getB().identity(), getC().identity());
    }

    public static <A, B, C> Monoid<T3<A, B, C>> from(Monoid<A> monoidA, Monoid<B> monoidB, Monoid<C> monoidC) {
        return new T3Monoid<A, B, C>() {
            @Override
            protected Monoid<A> getA() {
                return monoidA;
            }

            @Override
            protected Monoid<B> getB() {
                return monoidB;
            }

            @Override
            protected Monoid<C> getC() {
                return monoidC;
            }
        };
    }
}
