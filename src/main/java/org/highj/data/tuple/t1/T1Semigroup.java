package org.highj.data.tuple.t1;

import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;

public abstract class T1Semigroup<A> implements Semigroup<T1<A>> {

    protected abstract Semigroup<A> getA();

    @Override
    public T1<A> dot(T1<A> x, T1<A> y) {
        return Tuple.of(getA().dot(x._1(), y._1()));
    }

    public static <A> Semigroup<T1<A>> from(Semigroup<A> semigroupA) {
        return new T1Semigroup<A>() {
            @Override
            protected Semigroup<A> getA() {
                return semigroupA;
            }
        };
    }
}
