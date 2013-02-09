package org.highj.data.tuple.group;

import org.highj.data.tuple.T3;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public abstract class T3Group<A, B, C> extends T3Monoid<A, B, C> implements Group<T3<A, B, C>> {

    @Override
    protected abstract Group<A> getA();

    @Override
    protected abstract Group<B> getB();

    @Override
    protected abstract Group<C> getC();

    @Override
    public T3<A, B, C> inverse(T3<A, B, C> x) {
        return Tuple.of(getA().inverse(x._1()), getB().inverse(x._2()), getC().inverse(x._3()));
    }

    public static <A, B, C> Group<T3<A, B, C>> from(Group<A> groupA, Group<B> groupB, Group<C> groupC) {
        return new T3Group<A, B, C>() {
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
        };
    }

}
