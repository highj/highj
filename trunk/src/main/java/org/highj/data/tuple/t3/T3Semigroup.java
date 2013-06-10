package org.highj.data.tuple.t3;

import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;

public interface T3Semigroup<A, B, C> extends Semigroup<T3<A, B, C>> {

    public Semigroup<A> getA();

    public Semigroup<B> getB();

    public Semigroup<C> getC();


    @Override
    public default T3<A, B, C> dot(T3<A, B, C> x, T3<A, B, C> y) {
        return Tuple.of(getA().dot(x._1(), y._1()),
                getB().dot(x._2(), y._2()),
                getC().dot(x._3(), y._3()));
    }

    public static <A, B, C> Semigroup<T3<A, B, C>> from(Semigroup<A> semigroupA, Semigroup<B> semigroupB, Semigroup<C> semigroupC) {
        return new T3Semigroup<A, B, C>() {
            @Override
            public Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            public Semigroup<B> getB() {
                return semigroupB;
            }

            @Override
            public Semigroup<C> getC() {
                return semigroupC;
            }
        };
    }
}
