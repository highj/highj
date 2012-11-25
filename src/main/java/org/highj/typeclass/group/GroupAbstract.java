package org.highj.typeclass.group;

import org.highj.function.F1;
import org.highj.function.F2;

public class GroupAbstract<A> extends MonoidAbstract<A> implements Group<A> {


    private final F1<A, A> inverseFunction;

    public GroupAbstract(F2<A, A, A> dotFunction, A id, F1<A, A> inverseFunction) {
        super(dotFunction, id);
        this.inverseFunction = inverseFunction;
    }

    public GroupAbstract(Monoid<A> monoid, F1<A, A> inverseFunction) {
        this(monoid.dot(), monoid.identity(), inverseFunction);
    }

    @Override
    public A inverse(A a) {
        return inverseFunction.$(a);
    }

    @Override
    public F1<A, A> inverse() {
        return inverseFunction;
    }
}
