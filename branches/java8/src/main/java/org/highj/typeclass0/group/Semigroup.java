package org.highj.typeclass0.group;

import org.highj.data.collection.List;

/**
 * A structure supporting an associative operation "dot": dot(x,dot(y,z)) == dot(dot(x,y),z)
 */
public interface Semigroup<A> {

    public A dot(A x, A y);


    public default A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(dot(a, as.head()), as.tail());
    }

    public default A fold(A a, A... as) {
        return fold(a, List.of(as));
    }

}
