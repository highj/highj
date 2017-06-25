package org.highj.function;

import org.derive4j.hkt.__2;

public interface NF2<F,G> extends __2<NF2.µ,F,G> {
    class µ {}
    
    <B,C> __2<G,B,C> apply(__2<F,B,C> a);

    default <H> NF2<F,H> andThen(NF2<G,H> gh) {
        return compose(gh, this);
    }

    static <F> NF2<F,F> identity() {
        return new NF2<F,F>() {
            @Override
            public <B, C> __2<F, B, C> apply(__2<F, B, C> a) {
                return a;
            }
        };
    }

    static <F,G,H> NF2<F,H> compose(NF2<G,H> gh, NF2<F,G> fg) {
        return new NF2<F, H>() {
            @Override
            public <A, B> __2<H, A, B> apply(__2<F, A, B> ab) {
                return gh.apply(fg.apply(ab));
            }
        };
    }
}
