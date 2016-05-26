package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

/**
 * Natural Transformation
 * @param <F> Source Context
 * @param <G> Target Context
 */
public interface NF<F,G> extends __2<NF.µ,F,G> {
    class µ {}

    static <F,G> NF<F,G> narrow(__<__<NF.µ,F>,G> a) {
        return (NF<F,G>)a;
    }

    <A> __<G,A> apply(__<F,A> a);

    static <F_> NF<F_,F_> identity() {
        return new NF<F_,F_>() {
            @Override
            public <A> __<F_, A> apply(__<F_, A> a) {
                return a;
            }
        };
    }
}
