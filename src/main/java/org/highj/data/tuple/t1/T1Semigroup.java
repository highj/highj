package org.highj.data.tuple.t1;

import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Semigroup;

public interface T1Semigroup<A> extends Semigroup<T1<A>> {

    public Semigroup<A> getA();

    @Override
    public default T1<A> dot(T1<A> x, T1<A> y) {
        return T1.of(getA().dot(x._1(), y._1()));
    }

    public static <A> Semigroup<T1<A>> from(Semigroup<A> semigroupA) {
        return (T1Semigroup<A>) () -> semigroupA;
    }
}
