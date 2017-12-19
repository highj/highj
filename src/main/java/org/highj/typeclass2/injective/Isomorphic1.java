package org.highj.typeclass2.injective;

import org.derive4j.hkt.__;

/**
 * The class of isomorphic type constructors, i.e. those which can be cast to each other without loss of information.
 * @param <F>
 * @param <G>
 */
public interface Isomorphic1<F,G> extends Injective1<F,G> {

    <A> __<F, A> from(__<G, A> input);

    default <A> Injective<__<G,A>, __<F,A>> from() {
        return this::from;
    }

    default <A> Isomorphic<__<F,A>, __<G,A>> isomorphic() {
        return Isomorphic.of(f -> Isomorphic1.this.to(f), g -> Isomorphic1.this.from(g));
    }

    default Isomorphic1<G,F> inverse() {
        return new Isomorphic1<G, F>() {
            @Override
            public <A> __<G, A> from(__<F, A> input) {
                return Isomorphic1.this.to(input);
            }

            @Override
            public <A> __<F, A> to(__<G, A> input) {
                return Isomorphic1.this.from(input);
            }
        };
    }

    static <F> Isomorphic1<F,F> identity() {
        return new Isomorphic1<F, F>() {
            @Override
            public <A> __<F, A> from(__<F, A> input) {
                return input;
            }

            @Override
            public <A> __<F, A> to(__<F, A> input) {
                return input;
            }
        };
    }

}
