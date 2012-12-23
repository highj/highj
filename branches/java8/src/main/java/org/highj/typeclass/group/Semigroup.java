package org.highj.typeclass.group;

import org.highj.data.collection.List;
import org.highj.function.F2;

/**
 * A structure supporting an associative operation "dot": dot(x,dot(y,z)) == dot(dot(x,y),z)
 */
public interface Semigroup<A> {

    public A dot(A x, A y);

    public F2<A,A,A> dot();

    public A fold(A a, List<A> as);

    public A fold(A a, A ... as);
}
