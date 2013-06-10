package org.highj.data.tuple.t1;

import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Monoid;

public interface T1Monoid<A> extends T1Semigroup<A>, Monoid<T1<A>> {

    @Override
    public Monoid<A> getA();

    @Override
    public default T1<A> identity() {
        return Tuple.of(getA().identity());
    }

    public static <A> Monoid<T1<A>> from(Monoid<A> monoidA) {
        return (T1Monoid<A>) () -> monoidA;
    }
}
