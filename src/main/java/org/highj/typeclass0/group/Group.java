package org.highj.typeclass0.group;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * A <code>Monoid</code> where every element has an inverse.
 * x == inverse(inverse(x))
 * inverse(identity) == identity
 */
public interface Group<A> extends Monoid<A> {

    A inverse(A a);

    static <A> Group<A> create(A identity, BinaryOperator<A> fn, UnaryOperator<A> inv) {
        return new Group<A>() {
            @Override
            public A inverse(A a) {
                return inv.apply(a);
            }

            @Override
            public A identity() {
                return identity;
            }

            @Override
            public A apply(A x, A y) {
                return fn.apply(x,y);
            }
        };
    }
}
