package org.highj.data.tuple.t1;

import org.highj.data.tuple.T1;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass0.group.Group;

public abstract class T1Group<A> extends T1Monoid<A> implements Group<T1<A>> {

    @Override
    protected abstract Group<A> getA();

    @Override
    public T1<A> inverse(T1<A> x) {
        return Tuple.of(getA().inverse(x._1()));
    }

    public static <A> Group<T1<A>> from(Group<A> groupA) {
        return new T1Group<A>() {
            @Override
            protected Group<A> getA() {
                return groupA;
            }
        };
    }

}
