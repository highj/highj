package org.highj.data.tuple.t1;

import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public interface T1Group<A> extends T1Monoid<A>, Group<T1<A>> {

    @Override
    public Group<A> getA();

    @Override
    public default T1<A> inverse(T1<A> x) {
        return Tuple.of(getA().inverse(x._1()));
    }

    public static <A> Group<T1<A>> from(Group<A> groupA) {
        return (T1Group<A>) () -> groupA;
    }

}
