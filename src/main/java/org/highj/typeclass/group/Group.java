package org.highj.typeclass.group;

import org.highj.function.F1;

/**
 * A <code>Monoid</code> where every element has an inverse.
 * x == inverse(inverse(x))
 * inverse(identity) == identity
 */
public interface Group<A> extends Monoid<A> {
    public A inverse(A a);

    public F1<A,A> inverse();
}
