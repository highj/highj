package org.highj.data.tuple.t2;

import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;

public interface T2Semigroup<A, B> extends Semigroup<T2<A, B>> {

    public Semigroup<A> getA();

    public Semigroup<B> getB();

    @Override
    public default T2<A,B> dot(T2<A, B> x, T2<A, B> y) {
        return T2.of(getA().dot(x._1(), y._1()),
                getB().dot(x._2(), y._2()));
    }

    public static <A, B> Semigroup<T2<A, B>> from(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return new T2Semigroup<A, B>() {
            @Override
            public Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            public Semigroup<B> getB() {
                return semigroupB;
            }
        };
    }
}
