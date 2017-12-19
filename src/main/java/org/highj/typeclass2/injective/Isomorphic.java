package org.highj.typeclass2.injective;

import java.util.function.Function;

/**
 * The class of isomorphic types, i.e. those which can be cast to each other without loss of information.
 * @param <A> source type
 * @param <B> target type
 */
public interface Isomorphic<A, B> extends Injective<A, B> {

    A from(B input);

    static <A, B> Isomorphic<A, B> of(Function<A, B> fnTo, Function<B, A> fnFrom) {
        return new Isomorphic<A, B>() {
            @Override
            public A from(B input) {
                return fnFrom.apply(input);
            }

            @Override
            public B to(A input) {
                return fnTo.apply(input);
            }
        };
    }

    static <A> Isomorphic<A, A> identity() {
        return Isomorphic.of(a -> a, a -> a);
    }

    default Isomorphic<B, A> inverse() {
        return Isomorphic.of(Isomorphic.this::from, Isomorphic.this::to);
    }
}
