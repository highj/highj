package org.highj.typeclass0.group;

public enum Monoids {
    ;

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
