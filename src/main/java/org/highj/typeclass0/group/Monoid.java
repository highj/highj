package org.highj.typeclass0.group;

import org.highj.data.List;

import java.util.function.BinaryOperator;

/**
 * A <code>Semigroup</code> with an identity element: apply(x,identity) == apply(identity,x) == x
 */
public interface Monoid<A> extends Semigroup<A> {

    A identity();

    default A fold(List<A> as) {
        return fold(identity(), as);
    }

    default A times(A a, int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("argument must be non-negative");
        } else if (n == 0) {
            return identity();
        }
        A x = a;
        A y = identity();
        while (n > 1) {
            if (n % 2 == 1) {
                y = apply(x, y);
            }
            x = apply(x, x);
            n /= 2;
        }
        return apply(x, y);
    }

    static <A> Monoid<A> dual(Monoid<A> monoid) {
        return create(monoid.identity(), (x, y) -> monoid.apply(y, x));
    }

    static <A> Monoid<A> create(A identity, BinaryOperator<A> fn) {
        return new Monoid<A>() {
            @Override
            public A identity() {
                return identity;
            }

            @Override
            public A apply(A x, A y) {
                return fn.apply(x, y);
            }
        };
    }
}
