package org.highj.data.tuple.group;

import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public abstract class T1Monoid<A> extends T1Semigroup<A> implements Monoid<T1<A>> {

    @Override
    protected abstract Monoid<A> getA();

    @Override
    public T1<A> identity() {
        return Tuple.of(getA().identity());
    }

    public static <A> Monoid<T1<A>> from(Monoid<A> monoidA) {
        return new T1Monoid<A>() {
            @Override
            protected Monoid<A> getA() {
                return monoidA;
            }
        };
    }
}
