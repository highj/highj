package org.highj.typeclass2.injective;

import org.derive4j.hkt.__;

public interface Function1<F, G> {

    <A> __<G, A> apply1(__<F, A> input);

    default <H> Function1<F, H> andThen(Function1<G, H> that) {
        return new Function1<F, H>() {
            @Override
            public <A> __<H, A> apply1(__<F, A> input) {
                return that.apply1(Function1.this.apply1(input));
            }
        };
    }

    static <M> Function1<M, M> identity() {
        return new Function1<M, M>() {
            @Override
            public <A> __<M, A> apply1(__<M, A> input) {
                return input;
            }
        };
    }
}
