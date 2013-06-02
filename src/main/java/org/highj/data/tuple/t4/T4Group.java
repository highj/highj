package org.highj.data.tuple.t4;

import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public abstract class T4Group<A, B, C, D> extends T4Monoid<A, B, C, D> implements Group<T4<A, B, C, D>> {

    @Override
    protected abstract Group<A> getA();

    @Override
    protected abstract Group<B> getB();

    @Override
    protected abstract Group<C> getC();

    @Override
    protected abstract Group<D> getD();

    @Override
    public T4<A, B, C, D> inverse(T4<A, B, C, D> x) {
        return Tuple.of(getA().inverse(x._1()),
                getB().inverse(x._2()),
                getC().inverse(x._3()),
                getD().inverse(x._4()));
    }

    public static <A, B, C, D> Group<T4<A, B, C, D>> from(Group<A> groupA, Group<B> groupB,
                                                          Group<C> groupC, Group<D> groupD) {
        return new T4Group<A, B, C, D>() {
            @Override
            protected Group<A> getA() {
                return groupA;
            }

            @Override
            protected Group<B> getB() {
                return groupB;
            }

            @Override
            protected Group<C> getC() {
                return groupC;
            }

            @Override
            protected Group<D> getD() {
                return groupD;
            }
        };
    }

}
