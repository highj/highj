package org.highj.typeclass2.injective;

import org.derive4j.hkt.__;

/**
 * An injective relationship of type constructors.
 *
 * Law:
 * if x != y then injective1.to(x) != injective1.to(y)
 *
 * @param <F> the source type constructor
 * @param <G> the target type constructor
 */
public interface Injective1<F, G> extends Function1<F,G> {

    <A> __<G, A> to(__<F, A> input);

    default <A> Injective<__<F,A>, __<G,A>> to() {
        return this::to;
    }

    @Override
    default <A> __<G, A> apply1(__<F, A> input) {
        return to(input);
    }

    default <H> Injective1<F, H> andThen(Injective1<G, H> that) {
         return new Injective1<F, H>() {
             @Override
             public <A> __<H, A> to(__<F, A> input) {
                 return that.to(Injective1.this.to(input));
             }
         };
    }

    static <M> Injective1<M, M> identity() {
        return new Injective1<M, M>() {
            @Override
            public <A> __<M, A> to(__<M, A> input) {
                return input;
            }
        };
    }
}
