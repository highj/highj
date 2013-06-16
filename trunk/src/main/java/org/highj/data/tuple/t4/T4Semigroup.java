package org.highj.data.tuple.t4;

import org.highj.data.tuple.T4;
import org.highj.typeclass0.group.Semigroup;

public interface T4Semigroup<A, B, C, D> extends Semigroup<T4<A, B, C, D>> {

    public Semigroup<A> getA();

    public Semigroup<B> getB();

    public Semigroup<C> getC();

    public Semigroup<D> getD();


    @Override
    public default T4<A, B, C, D> dot(T4<A, B, C, D> x, T4<A, B, C, D> y) {
        return T4.of(getA().dot(x._1(), y._1()),
                getB().dot(x._2(), y._2()),
                getC().dot(x._3(), y._3()),
                getD().dot(x._4(), y._4()));
    }

    public static <A, B, C, D> Semigroup<T4<A, B, C, D>> from(Semigroup<A> semigroupA, Semigroup<B> semigroupB,
                                                              Semigroup<C> semigroupC, Semigroup<D> semigroupD) {
        return new T4Semigroup<A, B, C, D>() {
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

            @Override
            public Semigroup<D> getD() {
                return semigroupD;
            }
        };
    }
}
