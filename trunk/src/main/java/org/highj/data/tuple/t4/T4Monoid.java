package org.highj.data.tuple.t4;

import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public interface T4Monoid<A, B, C, D> extends T4Semigroup<A, B, C, D>, Monoid<T4<A, B, C, D>> {

    @Override
    public Monoid<A> getA();

    @Override
    public Monoid<B> getB();

    @Override
    public Monoid<C> getC();

    @Override
    public Monoid<D> getD();

    @Override
    public default T4<A, B, C, D> identity() {
        return Tuple.of(getA().identity(), getB().identity(), getC().identity(), getD().identity());
    }

    public static <A, B, C, D> Monoid<T4<A, B, C, D>> from(Monoid<A> monoidA, Monoid<B> monoidB,
                                                           Monoid<C> monoidC, Monoid<D> monoidD) {
        return new T4Monoid<A, B, C, D>() {
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

            @Override
            public Monoid<D> getD() {
                return monoidD;
            }
        };
    }
}
