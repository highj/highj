package org.highj.data.tuple.group;

import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public abstract class T4Monoid<A, B, C, D> extends T4Semigroup<A, B, C, D> implements Monoid<T4<A, B, C, D>> {

    @Override
    protected abstract Monoid<A> getA();

    @Override
    protected abstract Monoid<B> getB();

    @Override
    protected abstract Monoid<C> getC();

    @Override
    protected abstract Monoid<D> getD();

    @Override
    public T4<A, B, C, D> identity() {
        return Tuple.of(getA().identity(), getB().identity(), getC().identity(), getD().identity());
    }

    public static <A, B, C, D> Monoid<T4<A, B, C, D>> from(Monoid<A> monoidA, Monoid<B> monoidB,
                                                           Monoid<C> monoidC, Monoid<D> monoidD) {
        return new T4Monoid<A, B, C, D>() {
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

            @Override
            protected Monoid<D> getD() {
                return monoidD;
            }
        };
    }
}
