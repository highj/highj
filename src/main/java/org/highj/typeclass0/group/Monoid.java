package org.highj.typeclass0.group;

import org.highj.data.collection.List;

/**
 * A <code>Semigroup<code/> with an identity element: dot(x,identity) == dot(identity,x) == x
 */
public interface Monoid<A> extends Semigroup<A> {

    public A identity();

    public default A fold(List<A> as) {
        return fold(identity(), as);
    }

    public static <A> Monoid<A> dual(Monoid<A> monoid) {
        return new Monoid<A>() {

            @Override
            public A identity() {
                return monoid.identity();
            }

            @Override
            public A dot(A x, A y) {
                return monoid.dot(y, x);
            }
        };
    }
}
