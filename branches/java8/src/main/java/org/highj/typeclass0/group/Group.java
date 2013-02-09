package org.highj.typeclass0.group;

/**
 * A <code>Monoid</code> where every element has an inverse.
 * x == inverse(inverse(x))
 * inverse(identity) == identity
 */
public interface Group<A> extends Monoid<A> {

    public A inverse(A a);
}
