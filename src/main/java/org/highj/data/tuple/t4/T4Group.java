package org.highj.data.tuple.t4;

import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public interface T4Group<A, B, C, D> extends T4Monoid<A, B, C, D>, Group<T4<A, B, C, D>> {

    @Override
    public Group<A> getA();

    @Override
    public Group<B> getB();

    @Override
    public Group<C> getC();

    @Override
    public Group<D> getD();

    @Override
    public default T4<A, B, C, D> inverse(T4<A, B, C, D> x) {
        return Tuple.of(getA().inverse(x._1()),
                getB().inverse(x._2()),
                getC().inverse(x._3()),
                getD().inverse(x._4()));
    }

    public static <A, B, C, D> Group<T4<A, B, C, D>> from(Group<A> groupA, Group<B> groupB,
                                                          Group<C> groupC, Group<D> groupD) {
        return new T4Group<A, B, C, D>() {
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

            @Override
            public Group<D> getD() {
                return groupD;
            }
        };
    }

}
