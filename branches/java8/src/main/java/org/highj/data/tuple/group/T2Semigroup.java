package org.highj.data.tuple.group;

import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;

public abstract class T2Semigroup<A, B> implements Semigroup<T2<A, B>> {

    protected abstract Semigroup<A> getA();

    protected abstract Semigroup<B> getB();

    @Override
    public T2<A, B> dot(T2<A, B> x, T2<A, B> y) {
        return Tuple.of(getA().dot(x._1(), y._1()),
                getB().dot(x._2(), y._2()));
    }

    public static <A, B> Semigroup<T2<A, B>> from(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return new T2Semigroup<A, B>() {
            @Override
            protected Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            protected Semigroup<B> getB() {
                return semigroupB;
            }
        };
    }
}
