package org.highj.data.tuple.t2;

import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public abstract class T2Group<A, B> extends T2Monoid<A, B> implements Group<T2<A, B>> {

    @Override
    protected abstract Group<A> getA();

    @Override
    protected abstract Group<B> getB();

    @Override
    public T2<A, B> inverse(T2<A, B> x) {
        return Tuple.of(getA().inverse(x._1()), getB().inverse(x._2()));
    }

    public static <A, B> Group<T2<A, B>> from(Group<A> groupA, Group<B> groupB) {
        return new T2Group<A, B>() {
            @Override
            protected Group<A> getA() {
                return groupA;
            }

            @Override
            protected Group<B> getB() {
                return groupB;
            }
        };
    }

}
