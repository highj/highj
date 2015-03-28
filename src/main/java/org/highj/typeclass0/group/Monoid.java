package org.highj.typeclass0.group;

import org.highj.data.collection.List;

import java.util.function.BinaryOperator;

/**
 * A <code>Semigroup<code/> with an identity element: apply(x,identity) == apply(identity,x) == x
 */
public interface Monoid<A> extends Semigroup<A> {

    public A identity();

    public default A fold(List<A> as) {
        return fold(identity(), as);
    }

    public static <A> Monoid<A> dual(Monoid<A> monoid) {
        return create(monoid.identity(), (x,y) -> monoid.apply(y,x));
    }

    public static <A> Monoid<A> create(A identity, BinaryOperator<A> fn) {
        return new Monoid<A>() {
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
