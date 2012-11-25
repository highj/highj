package org.highj.typeclass.group;

import org.highj.data.collection.List;
import org.highj.function.F2;

public class SemigroupAbstract<A> implements Semigroup<A> {

    private final F2<A, A, A> dotFunction;

    public SemigroupAbstract(F2<A, A, A> dotFunction) {
        this.dotFunction = dotFunction;
    }

    @Override
    public A dot(A x, A y) {
        return dotFunction.$(x,y);
    }

    @Override
    public F2<A, A, A> dot() {
        return dotFunction;
    }

    @Override
    public A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(dot(a,as.head()), as.tail()) ;
    }

    @Override
    public A fold(A a, A ... as) {
        return fold(a, List.of(as)) ;
    }

}
