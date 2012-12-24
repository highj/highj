package org.highj.typeclass.group;

import org.highj.data.collection.List;

/**
 * A <code>Semigroup<code/> with an identity element: dot(x,identity) == dot(identity,x) == x
 */
public interface Monoid<A> extends Semigroup<A> {
    public A identity();

    public default A fold(List<A> as) {
        return fold(identity(), as);
    }
}
