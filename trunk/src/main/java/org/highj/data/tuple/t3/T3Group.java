package org.highj.data.tuple.t3;

import org.highj.data.tuple.T3;
import org.highj.typeclass0.group.Group;

public interface T3Group<A, B, C> extends T3Monoid<A, B, C>, Group<T3<A, B, C>> {

    @Override
    public Group<A> getA();

    @Override
    public Group<B> getB();

    @Override
    public Group<C> getC();

    @Override
    public default T3<A, B, C> inverse(T3<A, B, C> x) {
        return T3.of(getA().inverse(x._1()), getB().inverse(x._2()), getC().inverse(x._3()));
    }

    public static <A, B, C> Group<T3<A, B, C>> from(Group<A> groupA, Group<B> groupB, Group<C> groupC) {
        return new T3Group<A, B, C>() {
            @Override
            public Group<A> getA() {
                return groupA;
            }

            @Override
            public Group<B> getB() {
                return groupB;
            }

            @Override
            public Group<C> getC() {
                return groupC;
            }
        };
    }

}
