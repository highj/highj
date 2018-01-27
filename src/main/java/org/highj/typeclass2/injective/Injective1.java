package org.highj.typeclass2.injective;

import org.derive4j.hkt.__;
import org.highj.function.NF;

/**
 * An injective relationship of type constructors.
 * <p>
 * Law:
 * if x != y then injective1.to(x) != injective1.to(y)
 *
 * @param <F> the source type constructor
 * @param <G> the target type constructor
 */
public interface Injective1<F, G> extends NF<F, G> {

    <A> __<G, A> to(__<F, A> input);

    @Override
    default <A> __<G, A> apply(__<F, A> a) {
        return to(a);
    }

    default <A> Injective<__<F, A>, __<G, A>> to() {
        return this::to;
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
