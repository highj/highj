package org.highj.data.tuple.group;

import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Semigroup;

public abstract class T4Semigroup<A, B, C, D> implements Semigroup<T4<A, B, C, D>> {

    protected abstract Semigroup<A> getA();

    protected abstract Semigroup<B> getB();

    protected abstract Semigroup<C> getC();

    protected abstract Semigroup<D> getD();


    @Override
    public T4<A, B, C, D> dot(T4<A, B, C, D> x, T4<A, B, C, D> y) {
        return Tuple.of(getA().dot(x._1(), y._1()),
                getB().dot(x._2(), y._2()),
                getC().dot(x._3(), y._3()),
                getD().dot(x._4(), y._4()));
    }

    public static <A, B, C, D> Semigroup<T4<A, B, C, D>> from(Semigroup<A> semigroupA, Semigroup<B> semigroupB,
                                                              Semigroup<C> semigroupC, Semigroup<D> semigroupD) {
        return new T4Semigroup<A, B, C, D>() {
            @Override
            protected Semigroup<A> getA() {
                return semigroupA;
            }

            @Override
            protected Semigroup<B> getB() {
                return semigroupB;
            }

            @Override
            protected Semigroup<C> getC() {
                return semigroupC;
            }

            @Override
            protected Semigroup<D> getD() {
                return semigroupD;
            }
        };
    }
}
