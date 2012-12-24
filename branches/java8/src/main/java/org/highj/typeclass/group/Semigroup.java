package org.highj.typeclass.group;

import org.highj.data.collection.List;
import org.highj.function.F2;

/**
 * A structure supporting an associative operation "dot": dot(x,dot(y,z)) == dot(dot(x,y),z)
 */
public interface Semigroup<A> {


    public F2<A,A,A> dot();

    public default A dot(A x, A y) {
        return dot().$(x, y);
    }


    public default A fold(A a, List<A> as) {
        return as.isEmpty() ? a : fold(dot(a, as.head()), as.tail());
    }

    public default A fold(A a, A... as) {
        return fold(a, List.of(as));
    }

}
