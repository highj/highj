package org.highj.data.tuple.t2;

import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Group;

public interface T2Group<A, B> extends T2Monoid<A, B>, Group<T2<A, B>> {

    @Override
    public Group<A> getA();

    @Override
    public Group<B> getB();

    @Override
    public default T2<A, B> inverse(T2<A, B> x) {
        return T2.of(getA().inverse(x._1()), getB().inverse(x._2()));
    }

    public static <A, B> Group<T2<A, B>> from(Group<A> groupA, Group<B> groupB) {
        return new T2Group<A, B>() {
            @Override
            public Group<A> getA() {
                return groupA;
            }

            @Override
            public Group<B> getB() {
                return groupB;
            }
        };
    }

}
